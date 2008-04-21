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

//dotGraph: 'strict'? ('graph' | 'digraph') ID;// '{' statement* '}';

dotGraph: ID+;// '{' statement* '}';

//statement: (ID WS);

//ID: ('"' .* '"' |  ('_' | 'a'..'z' |'A'..'Z' ) (INT | 'a'..'z' |'A'..'Z' )* );

ID: ('a'..'z' |'A'..'Z')+;

INT : '0'..'9'+ ;

NEWLINE:'\r'? '\n';

WS: (' ' |'\t' | '\r' | '\n')+ { skip(); };


