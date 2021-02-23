import java_cup.runtime.Symbol;
import org.junit.jupiter.api.Test;

import java.io.Reader;
import java.io.StringReader;

public class ParserTests {

    Symbol parseProgram(Reader program) {
        Yylex lexer = new Yylex(program);
        parser parser = new parser(lexer);
        Symbol parserTree = null;
        try {
            parserTree = parser.parse();
        } catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return parserTree;
    }

    /**
     * The test fail if a exception is thrown.
     * If the parser is correct, the test pass automatically.
     */
    @Test
    void testParser() {
        String program = """
                    // This is a commentary.
                    
                    /*
                     * And this is a block commentary.
                     */
                     
                    skip;
                    i := 1+(0-2*3)*6+2-6+(0-1*6);
                    print(i);
                    x := 3;
                    
                    if(1 <= 3 && x = 3) then begin
                        x := 5;
                        y := 2
                    end
                    else
                        z := 10;

                    print(x);
                    print(y);
                    print(z);
                    
                    i := 0;
                    while(i <= 5) do
                       i := i + 1;
                    print(i);
                    
                    begin
                        begin
                            begin
                                x := 5
                            end;
                            x := 4;
                            y := 3;
                            z := 2;
                            while(false) do
                                while(!true) do
                                    z := 0-1;
                            if(true) then
                                x := 0-1
                            else
                                if(!true) then
                                    y := 0 - 1
                                else
                                    i := 0 - 1
                        end;
                        i := 1
                    end
                        
                """;

        parseProgram(new StringReader(program));
    }

    @Test
    void simpleTestParser() {
        String program = "skip";
        parseProgram(new StringReader(program));
    }
}
