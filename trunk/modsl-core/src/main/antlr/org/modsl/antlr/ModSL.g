grammar ModSL;

options {
	language = Java;
}

@lexer::header {
	package org.modsl.antlr;
}

@parser::header {
	package org.modsl.antlr;
}

emptyRule: ;

line :	NEWLINE;

NEWLINE	:	'\r'? '\n';
