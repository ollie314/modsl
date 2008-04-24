grammar Dot;

options {
	language = Java;
}

@lexer::header {
	package org.modsl.antlr.dot;
}

@parser::header {
	package org.modsl.antlr.dot;
	import org.modsl.core.agt.*;
	import org.modsl.core.agt.dot.*;
	import java.util.Deque;
	import java.util.LinkedList;
}

@parser::members {
	public Node<DotType> root, cnode;
	protected Deque<Node<DotType>> nodes = new LinkedList<Node<DotType>>();
	protected DotFactory factory = new DotFactory();
}

dotGraph 
	@init{ root = factory.createRootNode(); cnode = root; }
	@after { root.postCreate(); }
	: 'graph' ID '{' statement* '}' { root.setName($ID.text); };

statement: (nodeStatement | edgeStatement) ';';

nodeStatement
	@init {	nodes.addFirst(cnode); }
	@after { cnode = nodes.removeFirst(); }
	: ID attributeList? { Node n = factory.createNode($ID); cnode.add(n); cnode = n; };

edgeStatement: ids+=ID EDGEOP ids+=ID (EDGEOP ids+=ID)* attributeList? { factory.createEdges(cnode, $ids); };

attributeList: '[' attribute (',' attribute)* ']';

attribute: key=ID '=' value=ID; // ignored for now

EDGEOP: '->' | '--';
ID: ('"' .* '"' |  ('_' | 'a'..'z' |'A'..'Z' ) (INT | '_' | 'a'..'z' |'A'..'Z')* | INT);
fragment INT : '0'..'9'+ ;
NEWLINE:'\r'? '\n';
WS: (' ' |'\t' | '\r' | '\n')+ { skip(); };