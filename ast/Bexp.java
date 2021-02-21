package ast;

import visitor.Visitor;

public abstract class Bexp {
    public abstract void accept(Visitor v);
}