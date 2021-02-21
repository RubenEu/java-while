package ast;

import visitor.Visitor;

public class Block extends Stm implements ASTNode {
    private Stm st;

    public Block(Stm st) {
        this.st = st;
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