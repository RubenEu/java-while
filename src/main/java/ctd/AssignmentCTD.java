package ctd;

public class AssignmentCTD extends CTD {
    
    public AssignmentCTD(Object operation, Object y, Object z, Object x) {
        op = operation;
        arg1 = y;
        arg2 = z;
        result = x;
    }

    public String toString() {
        return "\t" + result + " = " + arg1 + " " + op + " " + arg2 + ";";
    }
}
