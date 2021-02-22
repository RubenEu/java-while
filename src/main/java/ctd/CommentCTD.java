package ctd;

public class CommentCTD extends CTD {
    
    public CommentCTD(Object comment) {
        result = comment;
    }
    public String toString() {
        return "\t" + "# " + result;
    }
}
