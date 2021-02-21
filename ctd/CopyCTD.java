package ctd;

public class CopyCTD extends CTD {

    public CopyCTD(Object y, Object x) {
        op = null;
        arg1 = y;
        arg2 = null;
        result = x;
    }
    
    public String toString() {
        return "\t" + result + " = " + arg1 + ";";
    }
    
}
