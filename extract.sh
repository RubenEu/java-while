#!/bin/bash
mkdir while-java-compiler
cp -r src/main/java/* while-java-compiler/
cp src/main/cup/parser.cup while-java-compiler/WHILEC.cup
cp src/main/jflex/lexer.jflex while-java-compiler/WHILEC.flex
cp -r src/main/resources/code-examples/ while-java-compiler/