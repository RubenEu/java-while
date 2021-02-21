package visitor;

import ast.*;

public interface Visitor {
    public void visit(Program n);

    public void visit(IntLiteral n);
    public void visit(VariableExp n);
    public void visit(Add n);
    public void visit(Mul n);
    public void visit(Sub n);

    public void visit(True n);
    public void visit(False n);
    public void visit(Equal n);
    public void visit(LessEqual n);
    public void visit(Not n);
    public void visit(And n);

    public void visit(Assignment n);
    public void visit(Skip n);
    public void visit(Composition n);
    public void visit(IfElse n);
    public void visit(While n);
    public void visit(Block n);
    public void visit(Print n);
    
    public void visit(Variable n);
} 