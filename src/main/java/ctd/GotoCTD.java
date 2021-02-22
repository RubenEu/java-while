package ctd;

public class GotoCTD extends CTD {
    
    public GotoCTD(Object jump) {
        result = jump;
    }

    public String toString() {
        return "\t" + "goto " + result + ";";
    }
}
