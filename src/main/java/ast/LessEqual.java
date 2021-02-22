package ast;

import visitor.Visitor;

public class LessEqual extends Bexp implements ASTNode {
    private Aexp a1, a2;

    public LessEqual(Aexp a1, Aexp a2) {
        this.a1 = a1;
        this.a2 = a2;
    }

    public Aexp getA1() {
        return this.a1;
    }

    public void setA1(Aexp a1) {
        this.a1 = a1;
    }

    public Aexp getA2() {
        return this.a2;
    }

    public void setA2(Aexp a2) {
        this.a2 = a2;
    }
    
    public void accept(Visitor v) {
        v.visit(this);
    }
}