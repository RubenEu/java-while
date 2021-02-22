/**
 * <h1>JAVA-WHILE compiler.</h1>
 * 
 * @author Rubén García Rojas
 */

import java_cup.runtime.*;
import java.io.Reader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import ast.*;
import visitor.*;

public class WHILEC {

    public static Reader readProgramFile(String fileName) throws FileNotFoundException {
        Reader programCode = null;
        programCode = new FileReader(fileName);
        return programCode;
    }

    public static Program parseReader(Reader program) {
        Yylex lexer = new Yylex(program);
        parser parser = new parser(lexer);
        Symbol parserTree = null;
        try {
            parserTree = parser.parse();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return (Program) parserTree.value;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Program astProgram = parseReader(readProgramFile(args[0]));
        CTDVisitor ctdVisitor = new CTDVisitor();
        ctdVisitor.visit(astProgram);
        ctdVisitor.getCtds().forEach(System.out::println);
    }
}