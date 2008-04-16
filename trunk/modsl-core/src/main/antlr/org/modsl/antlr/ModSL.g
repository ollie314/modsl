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

NEWLINE	:	'\r'? '\n';
