package ast;

import visitor.Visitor;

public class Not extends Bexp implements ASTNode {
    private Bexp b;

    public Not(Bexp b) {
        this.b = b;
    }

    public Bexp getB() {
        return this.b;
    }

    public void setB(Bexp b) {
        this.b = b;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}