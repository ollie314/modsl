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
	public Node<BasicType> root, cnode;
	protected Deque<Node<BasicType>> nodes = new LinkedList<Node<BasicType>>();
	protected BasicFactory factory = new BasicFactory();
}

graph 
	@init{ root = factory.createRootNode(); cnode = root; }
	@after { root.accept(new NodeRefVisitor<BasicType>()); }
	: 'graph' ID '{' statement* '}' { root.setName($ID.text); };

statement: (nodeStatement | edgeStatement) ';';

nodeStatement
	@init {	nodes.addFirst(cnode); }
	@after { cnode = nodes.removeFirst(); }
	: ID { Node n = factory.createNode(cnode, $ID); cnode = n; };

edgeStatement: ids+=ID EDGEOP ids+=ID (EDGEOP ids+=ID)* { factory.createEdges(cnode, $ids); };

EDGEOP: '->' | '--';
ID: ('"' .* '"' |  ('_' | 'a'..'z' |'A'..'Z' ) (INT | '_' | 'a'..'z' |'A'..'Z')* | INT);
fragment INT : '0'..'9'+ ;
NEWLINE:'\r'? '\n';
WS: (' ' |'\t' | '\r' | '\n')+ { skip(); };