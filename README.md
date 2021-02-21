# Compilador WHILE

Este proyecto ha sido realizado con fines de aprendizaje.

En un inicio se realizará un compilador que convertirá el código introducido a un código intermedio. Este código intermedio podrá ser ejecutado con el [intérprete CTD](https://www.siette.org/siette.htdocs/pl/yacc-ctd/bin/). En un futuro se implementará con LLVM o MIR, o alguna variante que proporcione un ejecutable.

Las fases que el compilador cubre:

- Análisis léxico

- Análisis sintáctico

- Generación de código intermedio ([Intérprete: CTD](https://www.siette.org/siette.htdocs/pl/yacc-ctd/bin/))

El propósito de este proyecto es ir aprender e ir descubriendo las siguientes fases de generación de código, optimización, etc.

## Ejecutar el compilador

```bash
$ export CLASSPATH=.:/usr/share/java/cup.jar  # Necessary to compile CUP.
$ cup WHILE.cup                               # Generate CUP files.
$ jflex WHILE.flex                            # Generate JFLEX files.
$ javac *.java                                # Compile all the source code.
$ java WHILE <file>                           # Execute the compiler with the <file>.
```

El código de salida puede ser ejecutado con el [intérprete CTD](https://www.siette.org/siette.htdocs/pl/yacc-ctd/bin/).

```bash
$ java WHILE <file> | ./ctd
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

En la carpeta *code-examples* se encuentran ejemplos de las distintas sentencias
del lenguaje.