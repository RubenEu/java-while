package ast;

import visitor.Visitor;

public interface ASTNode {
    public void accept(Visitor v);
}