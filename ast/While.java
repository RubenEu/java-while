package ast;

import visitor.Visitor;

public class While extends Stm implements ASTNode {
    private Bexp b;
    private Stm st;

    public While(Bexp b, Stm st) {
        this.b = b;
        this.st = st;
    }

    public Bexp getB() {
        return this.b;
    }

    public void setB(Bexp b) {
        this.b = b;
    }

    public Stm getSt() {
        return this.st;
    }

    public void setSt(Stm st) {
        this.st = st;
    }
    
    public void accept(Visitor v) {
        v.visit(this);
    }
}