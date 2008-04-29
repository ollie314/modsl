grammar UML;

options {
	language = Java;
}

@lexer::header {
	package org.modsl.antlr.uml;
}

@parser::header {
	package org.modsl.antlr.uml;
	import org.modsl.core.agt.model.*;
	import org.modsl.core.agt.visitor.*;
	import org.modsl.core.lang.uml.*;
	import java.util.Deque;
	import java.util.LinkedList;
}

@parser::members {
	public Node<UMLMetaType> root, cnode;
	protected Deque<Node<UMLMetaType>> nodes = new LinkedList<Node<UMLMetaType>>();
	protected UMLCollabFactory collabFactory = new UMLCollabFactory();
}

collabDiagram 
	@init{ root = collabFactory.createRootNode(); cnode = root; }
	@after { root.accept(new NodeRefVisitor<UMLMetaType>()); }
	: 'graph' ID '{' statement* '}' { root.setName($ID.text); };

statement: (nodeStatement | edgeStatement) ';';

nodeStatement
	@init {	nodes.addFirst(cnode); }
	@after { cnode = nodes.removeFirst(); }
	: ID { Node n = collabFactory.createNode(cnode, $ID); cnode = n; };

edgeStatement: ids+=ID EDGEOP ids+=ID (EDGEOP ids+=ID)* { collabFactory.createEdges(cnode, $ids); };

EDGEOP: '->' | '--';
ID: ('"' .* '"' |  ('_' | 'a'..'z' |'A'..'Z' ) (INT | '_' | 'a'..'z' |'A'..'Z')* | INT);
fragment INT : '0'..'9'+ ;
NEWLINE:'\r'? '\n';
WS: (' ' |'\t' | '\r' | '\n')+ { skip(); };