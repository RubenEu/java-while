package ast;

import visitor.Visitor;

public abstract class Aexp {
    public abstract void accept(Visitor v);
}