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
	protected List<NodeLabel> curElements = new LinkedList<NodeLabel>();
	protected LinkedList<String> collabEdges = new LinkedList<String>();
}

diagram : classDiagram | collabDiagram;

collabDiagram 
	@init { graph = collabFactory.createGraph();  }
	@after { graph.accept(new NodeRefVisitor()); }
	: ('collab' | 'collaboration' | 'communication') 'diagram'? ID '{' collabStatement* '}' { graph.setName($ID.text); };

collabStatement: ID collab2Statement+ ';'  
	{ collabEdges.addFirst($ID.text); collabFactory.createEdges(graph, collabEdges); collabEdges.clear(); };
	//collabFactory.createNode(graph, $ID.text); curMethod = $method.text;

collab2Statement: EDGEOP ID '.' method  
	{ collabEdges.add($method.text); collabEdges.add($ID.text); };
	// curNode = collabFactory.createEdge(graph, $ID.text, curMethod, curNode); curMethod = $method.text; 

classDiagram 
	@init { graph = classFactory.createGraph();  }
	@after { graph.accept(new NodeRefVisitor()); }
	: 'class' 'diagram'? ID '{' (classStatement | interfaceStatement)* '}' { graph.setName($ID.text); };

classStatement:	'class' ID '{' classElementStatement* '}'
	{ classFactory.createClassNode(graph, $ID, curElements); curElements.clear(); }; 

interfaceStatement:	'interface' ID '{' classElementStatement* '}'  
	{ classFactory.createInterfaceNode(graph, $ID, curElements); curElements.clear(); }; 

classElementStatement: varClassElementStatement | staticVarClassElementStatement 
	| methodClassElementStatement | staticMethodClassElementStatement;

varClassElementStatement: ID ';' 
	{ curElements.add(classFactory.createNodeElement(UMLMetaType.CLASS_VAR_NODE_LABEL, $ID.text)); };

staticVarClassElementStatement: 'static' ID ';' 
	{ curElements.add(classFactory.createNodeElement(UMLMetaType.CLASS_STATIC_VAR_NODE_LABEL, $ID.text)); };

methodClassElementStatement: method ';' 
	{ curElements.add(classFactory.createNodeElement(UMLMetaType.CLASS_METHOD_NODE_LABEL, $method.text)); };

staticMethodClassElementStatement: 'static' method ';' 
	{ curElements.add(classFactory.createNodeElement(UMLMetaType.CLASS_STATIC_METHOD_NODE_LABEL, $method.text)); };

method: ID '(' (ID (',' ID)*)? ')';

EDGEOP: '->';
ID: ('_' | 'a'..'z' | 'A'..'Z' | ':') (INT | '_' | 'a'..'z' |'A'..'Z' | ':' | '[' | ']')*;
fragment INT : '0'..'9'+ ;
WS: (' ' | '\t' | NEWLINE)+ { skip(); };
fragment NEWLINE:'\r'? '\n';
