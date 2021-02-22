package ast;

import visitor.Visitor;

public class VariableExp extends Aexp implements ASTNode {
    private Variable v;

    public VariableExp(Variable v) {
        this.v = v;
    }

    public Variable getV() {
        return this.v;
    }

    public void setV(Variable v) {
        this.v = v;
    }
    
    public void accept(Visitor v) {
        v.visit(this);
    }
}