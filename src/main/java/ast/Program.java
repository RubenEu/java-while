package ast;

import visitor.Visitor;

public class Program implements ASTNode {
    private Stm st;

    public Program(Stm st) {
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