package ast;

import visitor.Visitor;

public class Skip extends Stm implements ASTNode {
    
    public void accept(Visitor v) {
        v.visit(this);
    }
}