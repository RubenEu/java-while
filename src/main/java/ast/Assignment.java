package ast;

import visitor.Visitor;

public class Assignment extends Stm implements ASTNode {
    private Variable v;
    private Aexp a;
    
    public Assignment(Variable v, Aexp a) {
        this.v = v;
        this.a = a;
    }

    public Variable getV() {
        return this.v;
    }

    public void setV(Variable v) {
        this.v = v;
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