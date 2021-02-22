package ctd;

public class CastingCTD extends CTD {
    
    public CastingCTD(Object type, Object y, Object x) {
        op = type;
        arg1 = y;
        arg2 = null;
        result = x;
    }

    public String toString() {
        return "\t" + result + " = (" + op + ") " + arg1 + ";";
    }
}
