# Compilador WHILEC

Proyecto realizado con fines de aprendizaje.

Compilador básico de la especificación del lenguaje WHILE del libro *Semantics with Applications (Nielson & Nielson)*.

Las fases que el compilador cubren son las siguientes:

- Análisis léxico ([JFLEX](src/main/jflex/lexer.jflex)).
- Análisis sintáctico ([CUP](src/main/cup/parser.cup))
- Generación de código intermedio CTD ([CTDVisitor](src/main/java/visitor/CTDVisitor.java))
    - Se puede ejecutar el código intermedio con el ejecutable [Intérprete: CTD](https://www.siette.org/siette.htdocs/pl/yacc-ctd/bin/).
- TODO: Intérprete del código intermedio
- TODO: Generación de ejecutable

## Ejecutar el compilador

Para generar el código CTD desde Maven:
```bash
# Ejecutar el main del compilador
$ mvn exec:java -Dexec.mainClass=WHILEC -Dexec.args="src/main/resources/code-examples/aexp-4.w"
```

Otra opción es extraer los ficheros principales (main, .cup, .jflex y clases auxiliares) y
moverlos a otra carpeta; compilándolos y ejecutándolos como a continuación:

```bash
$ export CLASSPATH=.:/usr/share/java/cup.jar  # Necessary to compile CUP.
$ cup WHILEC.cup                               # Generate CUP files.
$ jflex WHILEC.flex                            # Generate JFLEX files.
$ javac *.java                                # Compile all the source code.
$ java WHILEC <file>                           # Execute the compiler with the <file>.
```

para ello se proporciona el archivo .sh **extract.sh**, al ejecutarlo crea la carpeta *while-java-compiler/*
con todos los archivos necesarios.

```bash
$ ./extract.sh
```

El código de salida puede ser ejecutado con el [intérprete CTD](https://www.siette.org/siette.htdocs/pl/yacc-ctd/bin/).

```bash
$ java WHILEC <file> | ./ctd
```

## Código intermedio

Las instrucciones CTD utilizadas son las siguientes:

Instrucción         | Descripción
------------------- | ---------------------------------------------
x = a;              | Asigna el valor de a en la variable x;
x = a + b;          | Suma los valores de a y b y asigna el resultado en x.
x = a - b;          | Resta los valores de a y b asigna el resultado en x.
x = a * b;          | Multiplica los valores de a y b asigna el resultado en x.
x = a / b;          | Divide los valores de a y b asigna el resultado en x.
goto l;             | Salto incondicional. Salta a la posición de la etiqueta l.
if (a == b) goto l; | Salto condicional. Salta a la etiqueta l si se cumple que el valor de a es igual al de b.
if (a < b) goto l;  | Salto condicional. Salta a la etiqueta l si a es menor estricto que b.
l:                  | Posición de salto con el nombre l.
label l;            | Posición de salto con el nombre l. (Igual que la anterior pero de otra forma sintáctica).
print a;            | Imprime el valor de a por pantalla.
error;              | Inidica una situación de error.
halt;               | Detiene la ejecución.
\# ...              | Cualquier línea que comience con \# se considerará un comentario.

La especificación del código CTD ha sido extraída del [PLX-2014](https://www.siette.org/siette.htdocs/pl/cup-plxc/doc/PLX-2014.pdf) de la asignatura de Procesadores de Lenguaje impartida por Ricardo José Conejo Muñoz en la [UMA](https://uma.es/).

# Lenguaje WHILE

WHILE es un lenguaje de ejemplo extraído del libro Semantics with Applications (Nielson & Nielson).  

# Especificación de WHILE

Extraída del libro de Nielson & Nielson.

## Categorías sintácticas

- n will range over numerals, **Num**,
- x will range over variables, **Var**,
- a will range over arithmetic expressions, **Aexp**,
- b will range over boolean expressions, **Bexp**, and
- S will range over statements, **Stm**.

## Sintaxis abstracta

a ::= n | x | a1 + a2 | a1 ⋆ a2 | a1 − a2  
b ::= true | false | a1 = a2 | a1 ≤ a2 | ¬b | b1 ∧ b2  
S ::= x := a | skip | S1 ; S2 | if b then S1 else S2  
  | while b do S

## Sintaxis concreta

La sintaxis utilizada es parecida a Pascal.

El lenguaje WHILEC especificado en el libro no incluye la sentencia *print* pero para poder imprimir por la salida estándar
los resultados, se ha incluído en esta implementación.

program ::= stmList  
stmList ::= stm
   | stm ';' stmList
stm ::= variable ':=' aexp  
   | 'skip'  
   | 'if' '(' bexp ')' 'then' stm 'else' stm  
   | 'while' '(' bexp ')' 'do' stm  
   | 'begin' stmList 'end'  
   | 'print' '(' aexp ')'  
aexp ::= INT_LITERAL  
   | variable  
   | aexp '+' aexp  
   | aexp '*' aexp  
   | aexp '-' aexp  
   | '(' aexp ')'  
bexp ::= 'true'  
   | 'false'  
   | aexp '=' aexp  
   | aexp '<=' aexp  
   | '!' bexp  
   | bexp '&&' bexp  
   | '(' bexp ')'  

En la carpeta [*code-examples*](src/main/resources/code-examples/) se encuentran ejemplos de las distintas sentencias
del lenguaje.