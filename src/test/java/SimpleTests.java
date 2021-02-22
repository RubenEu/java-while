import static org.junit.jupiter.api.Assertions.assertEquals;

import ast.Program;
import ctd.CTD;
import org.junit.jupiter.api.Test;
import visitor.CTDVisitor;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimpleTests {

    List<String> executeProgram(String program) {
        // Read the source code.
        Reader r = new StringReader(program);
        Program p = WHILEC.parseReader(r);
        // Generate the intermediate code.
        CTDVisitor ctdVisitor = new CTDVisitor();
        ctdVisitor.visit(p);
        // Execute the program and get the results.
        CTDInterpreter ctdInterpreter = new CTDInterpreter();
        ctdInterpreter.execute(ctdVisitor.getCtds());
        return ctdInterpreter.getResults();
    }

    @Test
    void checkCompiler() {
        String program = "skip";
        assertEquals(new ArrayList<>(), executeProgram(program));
    }

    @Test
    void testSimpleAdd() {
        String program = "print(1+2)";
        List<Integer> expected = Arrays.asList(3);
        List<String> output = executeProgram(program);
        assertEquals(expected.toString(), output.toString());
    }

    @Test
    void testComplexAdd() {
        String program = "print(2+(3-1*2)+6*(3*4-2*10-1))";
        List<Integer> expected = Arrays.asList(-51);
        List<String> output = executeProgram(program);
        assertEquals(expected.toString(), output.toString());
    }

    @Test
    void programPrintSequence() {
        String program = """
                /*
                 * This program print from 0 to 10 manually.
                 */
                                
                print(1);
                print(1+1);
                print(1+1+1);
                print(1+1+1+1);
                print(1+1+1+1+1);
                print(1+1+1+1+1+1);
                print(1+1+1+1+1+1+1);
                print(1+1+1+1+1+1+1+1);
                print(1+1+1+1+1+1+1+1+1);
                print(1+1+1+1+1+1+1+1+1+1)
                """;
        List<Integer> expected = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        List<String> output = executeProgram(program);
        assertEquals(expected.toString(), output.toString());
    }

    @Test
    void programLoopCounter() {
        String program = """
                /*
                 * This program print from 0 to 10 sequentially,
                 * and then, exit the loop and print 11.
                 */
                                
                x := 0;
                while (x <= 10) do
                begin
                    print(x);
                    x := x + 1
                end;
                                
                print(x)
                """;
        List<Integer> expected = Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11);
        List<String> output = executeProgram(program);
        assertEquals(expected.toString(), output.toString());
    }
}
