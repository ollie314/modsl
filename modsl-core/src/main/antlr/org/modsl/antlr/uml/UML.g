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
	import org.modsl.core.lang.uml.factory.*;
	import java.util.Deque;
	import java.util.LinkedList;
}

@parser::members {
	public Graph graph;
	protected Deque<Graph> nodes = new LinkedList<Graph>();
	protected UMLCollabFactory collabFactory = new UMLCollabFactory();
	protected UMLClassFactory classFactory = new UMLClassFactory();
}

diagram : collabDiagram | classDiagram;

collabDiagram 
	@init { graph = collabFactory.createGraph();  }
	@after { graph.accept(new NodeRefVisitor()); }
	: ('collab' | 'collaboration' | 'communication') 'diagram'? ID '{' collabStatement* '}' { graph.setName($ID.text); };

collabStatement: ids+=ID EDGEOP ids+=ID '.' mds+=ID (EDGEOP ids+=ID '.' mds+=ID)* ';' 
	{ collabFactory.createEdges(graph, $ids, $mds); }; 

classDiagram 
	@init { graph = classFactory.createGraph();  }
	@after { graph.accept(new NodeRefVisitor()); }
	: 'class' 'diagram'? ID '{' classStatement* '}' { graph.setName($ID.text); };

classStatement: id=ID ';' 
	{ classFactory.createNode(graph, $id); }; 

EDGEOP: '->';
ID: ('_' | 'a'..'z' | 'A'..'Z' | ':') (INT | '_' | 'a'..'z' |'A'..'Z' | ':' | '(' | ')' | '[' | ']')*;
fragment INT : '0'..'9'+ ;
WS: (' ' | '\t' | NEWLINE)+ { skip(); };
fragment NEWLINE:'\r'? '\n';
