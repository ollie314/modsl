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

line returns [List<String> result]
@init {
    result = new ArrayList<String>();
}
	:	NEWLINE;

NEWLINE		:	('\r\n' | '\r' | '\n') /*{ newline(); }*/;

WS			: (' ' | '\t' | '\f' | NEWLINE) {$channel=HIDDEN;};

ML_COMMENT	:   '/*' .* '*/' {$channel=HIDDEN;};

COMMENT		: '//' ~('\n'|'\r')* NEWLINE {$channel=HIDDEN;};

INTLIT		: ('0'..'9')+;

CHARLIT		: '\''! . '\''!; 

ID 			: ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'0'..'9'|'_')* ;