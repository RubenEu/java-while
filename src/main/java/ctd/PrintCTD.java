package ctd;

public class PrintCTD extends CTD {
    
    public PrintCTD(Object y) {
        op = "print";
        arg1 = y;
        arg2 = null;
        result = null;
    }

    public String toString() {
        return "\t" + op + " " + arg1 + ";";
    }
}
