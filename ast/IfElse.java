package ast;

import visitor.Visitor;

public class IfElse extends Stm implements ASTNode {
    private Bexp b;
    private Stm st1, st2;    

    public IfElse(Bexp b, Stm st1, Stm st2) {
        this.b = b;
        this.st1 = st1;
        this.st2 = st2;
    }

    public Bexp getB() {
        return this.b;
    }

    public void setB(Bexp b) {
        this.b = b;
    }

    public Stm getSt1() {
        return this.st1;
    }

    public void setSt1(Stm st1) {
        this.st1 = st1;
    }

    public Stm getSt2() {
        return this.st2;
    }

    public void setSt2(Stm st2) {
        this.st2 = st2;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}