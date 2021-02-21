package visitor;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import ast.*;
import ctd.*;

/**
 * CTD visitor traverses the abstract syntax tree generating the ctd code.
 * 
 * @author Rubén García Rojas
 */
public class CTDVisitor implements Visitor {
    /** All the ctds generated in order. */
    private List<CTD> ctds;
    /** Information that some nodes save and can be retrieved later. */
    private Map<ASTNode, Configuration> configs;
    /** Counters for ctd variables and labels. */
    private Integer variableTemporal = 0;
    private Integer labelTemporal = 0;

    /** Auxiliar class that save nodes information. */
    private class Configuration {
        /** Variable in CTD that refers to that node result. */
        public String variable;
        /** Labels for Bexp when we need two jumps (true and false) */
        public String labelTrue, labelFalse;
    }

    public CTDVisitor() {
        ctds = new ArrayList<>();
        configs = new HashMap<>();
    }
    
    @Override
    public void visit(Program n) {
        // The program is composition of statements, so visit the statements
        // and then, print all the ctd's.
        n.getSt().accept(this);
    }

    @Override
    public void visit(IntLiteral n) {
        // This node doesn't generate any ctd, just an 'variable' output
        // to the ctd. So create it with the value of the n as String and
        // add a Configuration to this node.
        String ctdVariable = n.getIl().toString();
        Configuration nodeConfig = createConfiguration(n);
        nodeConfig.variable = ctdVariable;
    }

    @Override
    public void visit(VariableExp n) {
        // Get the variable identifier associated to the variable in the
        // VariableExp node. Then, assign that variable as this node variable.
        String variableIdentifier = n.getV().getI();
        Configuration nodeConfig = createConfiguration(n);
        nodeConfig.variable = variableIdentifier;
    }

    @Override
    public void visit(Add n) {
        // Visit left and right expression in order and get the variable
        // associated to each one, then create a temporal variable and attach it
        // to this node configuration and generate the ctd code.
        n.getA1().accept(this);
        String a1ExprVar = configs.get(n.getA1()).variable;
        n.getA2().accept(this);
        String a2ExprVar = configs.get(n.getA2()).variable;
        String tempVar = newTemporalVar();
        Configuration nodeConfig = createConfiguration(n);
        nodeConfig.variable = tempVar;
        ctds.add(new AssignmentCTD("+", a1ExprVar, a2ExprVar, tempVar));
    }

    @Override
    public void visit(Mul n) {
        // Like 'Add'.
        n.getA1().accept(this);
        String a1ExprVar = configs.get(n.getA1()).variable;
        n.getA2().accept(this);
        String a2ExprVar = configs.get(n.getA2()).variable;
        String tempVar = newTemporalVar();
        Configuration nodeConfig = createConfiguration(n);
        nodeConfig.variable = tempVar;
        ctds.add(new AssignmentCTD("*", a1ExprVar, a2ExprVar, tempVar));
    }

    @Override
    public void visit(Sub n) {
        // Like 'Add'.
        n.getA1().accept(this);
        String a1ExprVar = configs.get(n.getA1()).variable;
        n.getA2().accept(this);
        String a2ExprVar = configs.get(n.getA2()).variable;
        String tempVar = newTemporalVar();
        Configuration nodeConfig = createConfiguration(n);
        nodeConfig.variable = tempVar;
        ctds.add(new AssignmentCTD("-", a1ExprVar, a2ExprVar, tempVar));
    }

    @Override
    public void visit(True n) {
        // Create the two labels (maybe in this case we only need one, but stm's
        // like if, need two labels in the if and else, it can be optimized if
        // we consider this case when evaluated condition is primitive 
        // (true, false). But for simplicity we'll consider that exists two
        // jumps.
        //
        // In this primitive case we directly jump to the True label, so
        // we add a CTD with a jump to the true label.
        Configuration nodeConfig = createConfiguration(n);
        nodeConfig.labelTrue = newLabel();
        nodeConfig.labelFalse = newLabel();
        ctds.add(new GotoCTD(nodeConfig.labelTrue));
    }

    @Override
    public void visit(False n) {
        // Complementary of True ASTNode.
        Configuration nodeConfig = createConfiguration(n);
        nodeConfig.labelTrue = newLabel();
        nodeConfig.labelFalse = newLabel();
        ctds.add(new GotoCTD(nodeConfig.labelFalse));
    }

    @Override
    public void visit(Equal n) {
        // Create the Configuration with true and false labels, and then
        // visit the expression and generate the ctd for conditional jump.
        Configuration nodeConfig = createConfiguration(n);
        nodeConfig.labelTrue = newLabel();
        nodeConfig.labelFalse = newLabel();
        n.getA1().accept(this);
        n.getA2().accept(this);
        String varLeft = configs.get(n.getA1()).variable;
        String varRight = configs.get(n.getA2()).variable;
        ctds.add(new IfCTD("==", varLeft, varRight, nodeConfig.labelTrue));
        ctds.add(new GotoCTD(nodeConfig.labelFalse));
    }

    @Override
    public void visit(LessEqual n) {
        // Like Equal node.
        //
        // In this case we need a1 <= a2, but in CTD we only have a1 == a2 or
        // a1 < a2. But we can think in the complentary. a1 <= a2 is false when
        // a1 > a2 => a2 < a1. So we can make the condition with the labels
        // in the reverse order. We jump to the false label when the condition
        // is true (in the original condition, false, so we jump to the desired
        // label).
        Configuration nodeConfig = createConfiguration(n);
        nodeConfig.labelTrue = newLabel();
        nodeConfig.labelFalse = newLabel();
        n.getA1().accept(this);
        n.getA2().accept(this);
        String varLeft = configs.get(n.getA1()).variable;
        String varRight = configs.get(n.getA2()).variable;
        ctds.add(new IfCTD("<", varRight, varLeft, nodeConfig.labelFalse));
        ctds.add(new GotoCTD(nodeConfig.labelTrue));
    }

    @Override
    public void visit(Not n) {
        // n is a Bexp, so the only thing that we have to do is create a
        // configuration to this node and invert the labels of the Bexp nested.
        Configuration nodeConfig = createConfiguration(n);
        n.getB().accept(this);
        nodeConfig.labelTrue = configs.get(n.getB()).labelFalse;
        nodeConfig.labelFalse = configs.get(n.getB()).labelTrue;
    }

    @Override
    public void visit(And n) {
        // We have to visit each condition and chain the tags between and then
        // generate the condition with the labels of the last condition.
        Configuration nodeConfig = createConfiguration(n);
        String labelTrueB1, labelFalseB1, labelTrueB2, labelFalseB2;
        n.getB1().accept(this);
        labelTrueB1 = configs.get(n.getB1()).labelTrue;
        labelFalseB1 = configs.get(n.getB1()).labelFalse;
        ctds.add(new LabelCTD(labelTrueB1));
        n.getB2().accept(this);
        labelTrueB2 = configs.get(n.getB2()).labelTrue;
        labelFalseB2 = configs.get(n.getB2()).labelFalse;
        ctds.add(new LabelCTD(labelFalseB1));
        ctds.add(new GotoCTD(labelFalseB2));
        nodeConfig.labelTrue = labelTrueB2;
        nodeConfig.labelFalse = labelFalseB2;
    }

    @Override
    public void visit(Assignment n) {
        // First, visit the expression in the right side and then assign to the
        // left variable with a ctd.
        n.getA().accept(this);
        String rightExprVar = configs.get(n.getA()).variable;
        String leftVarIdent = n.getV().getI();
        ctds.add(new CopyCTD(rightExprVar, leftVarIdent));
    }

    @Override
    public void visit(Skip n) {
        ctds.add(new CommentCTD("Skip"));
    }

    @Override
    public void visit(Composition n) {
        // Just visit in order, first the st1 and then st2.
        n.getSt1().accept(this);
        n.getSt2().accept(this);
    }

    @Override
    public void visit(IfElse n) {
        // The 'if' stm requires two labels:
        // If 'Condition = True' then execute st1 and then jump to the end of
        // the 'if' (avoid run the st2 attached to the else).
        // 'Condition = False', then jump to the else condition.
        // 
        // if (Bexp) then Stm else Stm 
        //
        // LabelTrue:
        //     Statement_true
        //     Jump to EndIf    -- Avoid execute Statement_false.
        // LabelFalse:
        //     Statement_false
        // EndIf:               -- Neccesary to avoid execution Statement_false
        //                         when the Cond is true.
        // ...
        //
        // The labels are created by the Bexp. We can find them in the
        // configuration as labelTrue and labelFalse. Visit the Bexp to generate
        // the configuration.
        n.getB().accept(this);
        String labelTrue = configs.get(n.getB()).labelTrue;
        String labelFalse = configs.get(n.getB()).labelFalse;
        String endIfLabel = newLabel();
        // Generate the ctd code as previously described.
        ctds.add(new LabelCTD(labelTrue));
        n.getSt1().accept(this);
        ctds.add(new GotoCTD(endIfLabel));
        ctds.add(new LabelCTD(labelFalse));
        n.getSt2().accept(this);
        ctds.add(new LabelCTD(endIfLabel));
    }

    @Override
    public void visit(While n) {
        // while (Bexp) do Stm
        //
        // LabelCheckCondition:
        //     Visit Bexp ASTNode              -- Generating the jumps.
        // LabelWhileStmBody = Bexp.LabelTrue  -- Label True: Execute the Stm.
        //     Visit Stm                       -- Generate the Stm ctd code.
        //     Jump 
        // LabelEndWhile = Bexp.LabelFalse
        //
        // Create the labels, and generate de CTD as previously described
        // pseudo-code.
        String labelCheckCondition = newLabel();
        String labelWhileStmBody; // Assign after visit While.Bexp
        String labelEndWhile;     // Assign after visit While.Bexp
        // Generating the ctd code.
        ctds.add(new LabelCTD(labelCheckCondition));
        n.getB().accept(this);
        labelWhileStmBody = configs.get(n.getB()).labelTrue;
        labelEndWhile = configs.get(n.getB()).labelFalse;
        ctds.add(new LabelCTD(labelWhileStmBody));
        n.getSt().accept(this);
        ctds.add(new GotoCTD(labelCheckCondition));
        ctds.add(new LabelCTD(labelEndWhile));
    }

    @Override
    public void visit(Block n) {
        // The block only visits the stm inside.
        n.getSt().accept(this);
    }

    @Override
    public void visit(Print n) {
        // Visit the expression inside and then generate de ctd code for the
        // evaluted expression.
        n.getA().accept(this);
        String variableOutput = configs.get(n.getA()).variable;
        ctds.add(new PrintCTD(variableOutput));
    }

    @Override
    public void visit(Variable n) {
        // Nothing to do.
    }
    
    public List<CTD> getCtds() {
        return ctds;
    }

    /**
     * Create the identifier of a new ctd variable (temporal).
     * The name is innaccessible by the programmer.
     * 
     * Then increments the temporal counter for the next requested temporal.
     * 
     * @return new temporal variable identifier.
     */
    private String newTemporalVar() {
        return "$t" + variableTemporal++;
    }
    
    private String newLabel() {
        return "L" + labelTemporal++;
    }

    /**
     * Create a configuration and associate it to an ASTNode.
     * @param n ASTNode with which it is associated.
     * @return The created configuration.
     */
    private Configuration createConfiguration(ASTNode n) {
        Configuration c = new Configuration();
        Configuration cPrevious = configs.put(n, c);
        // Check if there was a previous configuration for that node.
        if(cPrevious != null)
            throw new RuntimeException(
                "There was a configuration associated to that node.");
        return c;
    }
}