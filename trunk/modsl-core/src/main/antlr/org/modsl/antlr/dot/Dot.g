grammar Dot;

options {
	language = Java;
	output = AST;
}

tokens {
	ATTRIBUTE;
	ATTRIBUTE_LIST;
	EDGE;
	NODE_LIST;
}

@lexer::header {
package org.modsl.antlr.dot;
}

@parser::header {
package org.modsl.antlr.dot;
}

dotGraph: 'strict'? ('graph' | 'digraph') ID '{' statement* '}' -> ^(ID statement*) ;

statement: (nodeStatement | edgeStatement) ';'!;

nodeStatement: ID attributeList? -> ^(ID ^(ATTRIBUTE_LIST attributeList)?);

edgeStatement: ID EDGEOP ID (EDGEOP ID)* attributeList? -> ^(EDGE ^(NODE_LIST ID+) ^(ATTRIBUTE_LIST attributeList)?);

attributeList: '[' attribute (',' attribute)* ']' -> attribute+;

attribute: key=ID '=' value=ID -> ^(ATTRIBUTE $key $value);

EDGEOP: '->' | '--';
ID: ('"' .* '"' |  ('_' | 'a'..'z' |'A'..'Z' ) (INT | 'a'..'z' |'A'..'Z')* | INT);
fragment INT : '0'..'9'+ ;
NEWLINE:'\r'? '\n';
WS: (' ' |'\t' | '\r' | '\n')+ { skip(); };


