package ctd;

public class LabelCTD extends CTD {
    
    public LabelCTD(Object label) {
        result = label;
    }

    public String toString() {
        return result + ":";
    }
}
