package ctd;

public class IfCTD extends CTD {
    
    public IfCTD(Object operation, Object y, Object z, Object x) {
        op = operation;
        arg1 = y;
        arg2 = z;
        result = x;
    }

    public String toString() {
        return "\t" + "if (" + arg1 + " " + op + " " + arg2 + ") goto " + result + ";";
    }
}
