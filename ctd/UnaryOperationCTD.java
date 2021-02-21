package ctd;

public class UnaryOperationCTD extends CTD {

    public UnaryOperationCTD(Object operation, Object z, Object x) {
        op = operation;
        arg1 = null;
        arg2 = z;
        result = x;
    }
    
    public String toString() {
        return "\t" + result + " = " + op + " " + arg2 + ";";
    }
}
