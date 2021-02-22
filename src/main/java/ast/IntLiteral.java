package ast;

import visitor.Visitor;

public class IntLiteral extends Aexp implements ASTNode {
    private Integer il;

    public IntLiteral(Integer il) {
        this.il = il;
    }

    public Integer getIl() {
        return this.il;
    }

    public void setIl(Integer il) {
        this.il = il;
    }
    
    public void accept(Visitor v) {
        v.visit(this);
    }
}