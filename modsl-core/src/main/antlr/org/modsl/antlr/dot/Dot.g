grammar Dot;

options {
	language = Java;
	output = AST;
}

tokens {
	DOTGRAPH;
}

@lexer::header {
package org.modsl.antlr.dot;
}

@parser::header {
package org.modsl.antlr.dot;
}

dotGraph: 'strict'? dg=('graph' | 'digraph') ID '{' statement* '}' -> ^(DOTGRAPH[$dg, "DotGraph"] statement*) ;

statement: (nodeStatement | edgeStatement | attributeStatement)';'!;

nodeStatement: ID attributeList?;

edgeStatement: ID EDGEOP ID (EDGEOP ID);

attributeStatement: ('graph' | 'node' | 'edge') attributeList;

attributeList: '[' ID '=' ID (',' ID '=' ID)? ']';

EDGEOP: '->' | '--';
ID: ('"' .* '"' |  ('_' | 'a'..'z' |'A'..'Z' ) (INT | 'a'..'z' |'A'..'Z' )* );
INT : '0'..'9'+ ;
NEWLINE:'\r'? '\n';
WS: (' ' |'\t' | '\r' | '\n')+ { skip(); };


