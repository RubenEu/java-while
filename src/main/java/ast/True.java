package ast;

import visitor.Visitor;

public class True extends Bexp implements ASTNode {
    
    public void accept(Visitor v) {
        v.visit(this);
    }
}