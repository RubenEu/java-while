import java_cup.runtime.Symbol;

%%

// CUP
%cup
// Lexical errors
%char
%line
%column

%state BLOCKCOMMENT

// Flex constants
LineTerminator = \r|\n|\r\n
WhiteSpace = {LineTerminator} | [ \t\f]

Identifier = [:jletter:] [:jletterdigit:]*
IntegerLiteral = 0 | [1-9][0-9]*

%%

"("         { return new Symbol(sym.LPAREN); }
")"         { return new Symbol(sym.RPAREN); }
"+"         { return new Symbol(sym.PLUS); }
"*"         { return new Symbol(sym.MULT); }
"-"         { return new Symbol(sym.MINUS); }
"true"      { return new Symbol(sym.TRUE); }
"false"     { return new Symbol(sym.FALSE); }
"="         { return new Symbol(sym.EQUAL); }
"<="        { return new Symbol(sym.LESSEQUAL); }
"!"         { return new Symbol(sym.EXCLAMATION); }
"&&"        { return new Symbol(sym.AND); }
":="        { return new Symbol(sym.ASSIGN); }
"skip"      { return new Symbol(sym.SKIP); }
";"         { return new Symbol(sym.SEMICOLON); }
"if"        { return new Symbol(sym.IF); }
"else"      { return new Symbol(sym.ELSE); }
"then"      { return new Symbol(sym.THEN); }
"while"     { return new Symbol(sym.WHILE); }
"do"        { return new Symbol(sym.DO); }
"begin"     { return new Symbol(sym.BEGIN); }
"end"       { return new Symbol(sym.END); }
"print"     { return new Symbol(sym.PRINT); }

{Identifier}        { return new Symbol(sym.IDENTIFIER, yytext()); }
{IntegerLiteral}    { return new Symbol(sym.INTLITERAL, Integer.parseInt(yytext())); }

{WhiteSpace}                { }
"//"[^\r\n]*(\n|\r|\r\n)?   { }
"/*"                        { yybegin(BLOCKCOMMENT); }

// Error
[^]                 { throw new RuntimeException("Lexical error on line: " + yyline + ", column: " + yycolumn + "."); }

<BLOCKCOMMENT> {
    "*/"                { yybegin(YYINITIAL); }
    [^]                 { }
}