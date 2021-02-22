/**
 * <h1>JAVA-WHILE compiler.</h1>
 * 
 * @author Rubén García Rojas
 */

import java_cup.runtime.*;
import java.io.Reader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import ctd.CTD;

import ast.*;
import visitor.*;

public class WHILEC {

    public static List<CTD> compileFile(String file) throws FileNotFoundException {
        Reader programCode = null;
        try {
            programCode = new FileReader(file);
        } catch(FileNotFoundException e) {
            System.err.println("There was an error while reading the file:");
            e.printStackTrace();
            System.exit(1);
        } catch(Exception e) {
            System.err.println("There was a fatal error:");
            e.printStackTrace();
            System.exit(1);
        }
        Yylex lexer = new Yylex(programCode);
        parser parser = new parser(lexer);
        Symbol parserTree = null;
        try {
            parserTree = parser.parse();
        } catch(Exception e) {
            System.err.println("There was an error while parsing.");
            e.printStackTrace();
            System.exit(1);
        }
        Program programAst = (Program) parserTree.value;
        // Execute the CTD visitor and generate ctd output.
        CTDVisitor ctdVisitor = new CTDVisitor();
        ctdVisitor.visit(programAst);
        return ctdVisitor.getCtds();
    }

    public static void main(String[] args) throws FileNotFoundException {
        compileFile(args[0]).forEach(System.out::println);
    }
}