import ast.Program;
import ctd.CTD;
import visitor.CTDVisitor;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;

public class CTDTests {

    List<CTD> generateCtd(String program) {
        // Read the source code.
        Reader r = new StringReader(program);
        Program p = WHILEC.parseReader(r);
        // Generate the intermediate code.
        CTDVisitor ctdVisitor = new CTDVisitor();
        ctdVisitor.visit(p);
        return ctdVisitor.getCtds();
    }
}
