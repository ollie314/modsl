grammar Dot;

options {
	language = Java;
}

@lexer::header {
	package org.modsl.antlr.dot;
}

@parser::header {
	package org.modsl.antlr.dot;
	import org.modsl.agt.*;
	import java.util.Deque;
	import java.util.LinkedList;
}

@parser::members {
	public Node root, cnode;
	protected Deque<Node> nodes = new LinkedList<Node>();
}

dotGraph 
	@init{ root = AGTFactory.createRootNode(); cnode = root; }
	: 'graph' ID '{' statement* '}' { root.setName($ID.text); };

statement: (nodeStatement | edgeStatement) ';';

nodeStatement
	@init {	nodes.addFirst(cnode); }
	@after { cnode = nodes.removeFirst(); }
	: ID attributeList? { Node n = AGTFactory.createNode($ID); cnode.add(n); cnode = n; };

edgeStatement: ids+=ID EDGEOP ids+=ID (EDGEOP ids+=ID)* attributeList? { AGTFactory.createEdges(cnode, $ids); };

attributeList: '[' attribute (',' attribute)* ']';

attribute: key=ID '=' value=ID; // ignored for now

EDGEOP: '->' | '--';
ID: ('"' .* '"' |  ('_' | 'a'..'z' |'A'..'Z' ) (INT | '_' | 'a'..'z' |'A'..'Z')* | INT);
fragment INT : '0'..'9'+ ;
NEWLINE:'\r'? '\n';
WS: (' ' |'\t' | '\r' | '\n')+ { skip(); };