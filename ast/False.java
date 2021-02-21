package ast;

import visitor.Visitor;

public class False extends Bexp implements ASTNode {
    
    public void accept(Visitor v) {
        v.visit(this);
    }
}