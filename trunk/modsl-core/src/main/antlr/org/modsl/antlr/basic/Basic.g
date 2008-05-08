grammar Basic;

options {
	language = Java;
}

@lexer::header {
	package org.modsl.antlr.basic;
}

@parser::header {
	package org.modsl.antlr.basic;
	import org.modsl.core.agt.model.*;
	import org.modsl.core.agt.visitor.*;
	import org.modsl.core.lang.basic.*;
	import java.util.Deque;
	import java.util.LinkedList;
}

@parser::members {
	public Graph root;
	protected BasicFactory factory = new BasicFactory();
}

graph 
	@init{ root = factory.createRootNode(); }
	@after { root.accept(new NodeRefVisitor()); }
	: 'graph' ID '{' statement* '}' { root.setName($ID.text); };

statement: (nodeStatement | edgeStatement) ';';

nodeStatement : ID { Node n = factory.createNode(root, $ID);  };

edgeStatement: ids+=ID EDGEOP ids+=ID (EDGEOP ids+=ID)* { factory.createEdges(root, $ids); };

EDGEOP: '->' | '--';
ID: ('"' .* '"' |  ('_' | 'a'..'z' |'A'..'Z' ) (INT | '_' | 'a'..'z' |'A'..'Z')* | INT);
fragment INT : '0'..'9'+ ;
NEWLINE:'\r'? '\n';
WS: (' ' |'\t' | '\r' | '\n')+ { skip(); };