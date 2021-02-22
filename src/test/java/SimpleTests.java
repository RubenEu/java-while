import static org.junit.jupiter.api.Assertions.assertEquals;

import ast.Program;
import ctd.CTD;
import org.junit.jupiter.api.Test;
import visitor.CTDVisitor;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;

public class SimpleTests {

    List<CTD> generateCtd(String program) {
        Reader r = new StringReader(program);
        Program p = WHILEC.parseReader(r);
        CTDVisitor ctdVisitor = new CTDVisitor();
        ctdVisitor.visit(p);
        return ctdVisitor.getCtds();
    }

    @Test
    void checkCompiler() {
        String program = "skip";
    }
}
