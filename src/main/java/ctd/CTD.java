package ctd;

/**
 * CTD (Intermediate representation).
 * 
 * Implementation of the intermediate representation given by Conejo
 * specification's.
 * 
 * It can be found at the 6º page of:
 * https://www.siette.org/siette.htdocs/pl/cup-plxc/doc/PLX-2014.pdf
 * 
 * It's described in the README.md of this project too.
 */
public abstract class CTD {
    protected Object op;
    protected Object arg1;
    protected Object arg2;
    protected Object result;
    
    public abstract String toString();
    
    public Object getOp() {
        return op;
    }
    
    public Object getArg1() {
        return arg1;
    }
    
    public Object getArg2() {
        return arg2;
    }
    
    public Object getResult() {
        return result;
    }
    
    public void setOp(Object o) {
        op = o;
    }
    
    public void setArg1(Object o) {
        arg1 = o;
    }
    
    public void setArg2(Object o) {
        arg2 = o;
    }
    
    public void setResult(Object o) {
        result = o;
    }
}
