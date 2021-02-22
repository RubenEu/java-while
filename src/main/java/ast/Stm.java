package ast;

import visitor.Visitor;

public abstract class Stm {
    public abstract void accept(Visitor v);
}