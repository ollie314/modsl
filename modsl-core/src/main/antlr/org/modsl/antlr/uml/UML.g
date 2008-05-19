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
	protected Node curNode;
}

diagram : classDiagram | collabDiagram;

collabDiagram 
	@init { graph = collabFactory.createGraph();  }
	@after { graph.accept(new NodeRefVisitor()); }
	: ('collab' | 'collaboration' | 'communication') 'diagram'? ID '{' collabStatement* '}' { graph.setName($ID.text); };

collabStatement: ids+=ID EDGEOP ids+=ID '.' mds+=ID (EDGEOP ids+=ID '.' mds+=ID)* ';' 
	{ collabFactory.createEdges(graph, $ids, $mds); }; 

classDiagram 
	@init { graph = classFactory.createGraph();  }
	@after { graph.accept(new NodeRefVisitor()); }
	: 'class' 'diagram'? ID '{' (classStatement | interfaceStatement)* '}' { graph.setName($ID.text); };

classStatement:	'class' id=ID '{' classElementStatement* '}'
	{ curNode = classFactory.createClassNode(graph, $id); }; 

interfaceStatement:	'interface' id=ID '{' classElementStatement* '}'  
	{ curNode = classFactory.createInterfaceNode(graph, $id); }; 

classElementStatement: id=ID ';' 
	{ classFactory.createNodeElement(curNode, UMLMetaType.CLASS_VAR_NODE_LABEL, $id); };

EDGEOP: '->';
ID: ('_' | 'a'..'z' | 'A'..'Z' | ':') (INT | '_' | 'a'..'z' |'A'..'Z' | ':' | '(' | ')' | '[' | ']')*;
fragment INT : '0'..'9'+ ;
WS: (' ' | '\t' | NEWLINE)+ { skip(); };
fragment NEWLINE:'\r'? '\n';
