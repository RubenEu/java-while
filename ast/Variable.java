package ast;

import visitor.Visitor;

public class Variable implements ASTNode {
    private String i;

    public Variable(String i) {
        this.i = i;
    }

    public String getI() {
        return this.i;
    }

    public void setI(String i) {
        this.i = i;
    }
    
    public void accept(Visitor v) {
        v.visit(this);
    }
}