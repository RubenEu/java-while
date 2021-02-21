package ast;

import visitor.Visitor;

public class Print extends Stm implements ASTNode {
    private Aexp a;

    public Print(Aexp a) {
        this.a = a;
    }

    public Aexp getA() {
        return this.a;
    }

    public void setA(Aexp a) {
        this.a = a;
    }
    
    public void accept(Visitor v) {
        v.visit(this);
    }
}