grammar Dot;

options {
	language = Java;
	output = AST;
}

@lexer::header {
package org.modsl.antlr.dot;
}

@parser::header {
package org.modsl.antlr.dot;
}

emptyRule: ;

NEWLINE:'\r'? '\n';

WS: (' ' |'\t' | '\r' | '\n')+ { skip(); };


