grammar Dot;

options {
	language = Java;
}

@lexer::header {
package org.modsl.antlr.dot;
}

@parser::header {
package org.modsl.antlr.dot;
}

dotGraph: 'strict'? ('graph' | 'digraph') ID '{' statement* '}' { System.out.println("dotGraph " + $ID + " "); };

statement: (nodeStatement | edgeStatement) ';';

nodeStatement: ID attributeList? { System.out.println("node " + $ID + " "); };

edgeStatement: ids+=ID EDGEOP ids+=ID (EDGEOP ids+=ID)* attributeList? { System.out.println("edge " + $ids + " "); };

attributeList: '[' attribute (',' attribute)* ']';

attribute: key=ID '=' value=ID { System.out.println("attribute  " + $key + " " + $value + " "); };

EDGEOP: '->' | '--';
ID: ('"' .* '"' |  ('_' | 'a'..'z' |'A'..'Z' ) (INT | '_' | 'a'..'z' |'A'..'Z')* | INT);
fragment INT : '0'..'9'+ ;
NEWLINE:'\r'? '\n';
WS: (' ' |'\t' | '\r' | '\n')+ { skip(); };


