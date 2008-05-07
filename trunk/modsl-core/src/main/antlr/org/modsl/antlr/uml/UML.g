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

diagram : collabDiagram;

collabDiagram 
	@init{ root = collabFactory.createRootNode(); cnode = root; }
	@after { root.accept(new NodeRefVisitor<UMLMetaType>()); }
	: ('collab' | 'collaboration' | 'communication') 'diagram'? ID '{' collabStatement* '}' { root.setName($ID.text); };

collabStatement: ids+=ID EDGEOP ids+=ID '.' mds+=ID (EDGEOP ids+=ID '.' mds+=ID)* ';' 
	{ collabFactory.createEdges(cnode, $ids, $mds); }; 

EDGEOP: '->';
ID: ('_' | 'a'..'z' | 'A'..'Z' | ':') (INT | '_' | 'a'..'z' |'A'..'Z' | ':' | '(' | ')' | '[' | ']')*;
fragment INT : '0'..'9'+ ;
WS: (' ' | '\t' | NEWLINE)+ { skip(); };
fragment NEWLINE:'\r'? '\n';
