package ast;

import visitor.Visitor;

public class And extends Bexp implements ASTNode {
    private Bexp b1, b2;

    public And(Bexp b1, Bexp b2) {
        this.b1 = b1;
        this.b2 = b2;
    }

    public Bexp getB1() {
        return b1;
    }

    public void setB1(Bexp b1) {
        this.b1 = b1;
    }

    public Bexp getB2() {
        return this.b2;
    }

    public void setB2(Bexp b2) {
        this.b2 = b2;
    }
    
    public void accept(Visitor v) {
        v.visit(this);
    }
}