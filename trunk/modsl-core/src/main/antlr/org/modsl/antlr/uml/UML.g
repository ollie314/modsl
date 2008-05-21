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
	: ('collab' | 'collaboration' | 'communication') 'diagram'? ID '{' collabStmt* '}' { graph.setName($ID.text); };

collabStmt: ID collab2Stmt+ ';'  
	{ collabEdges.addFirst($ID.text); collabFactory.createEdges(graph, collabEdges); collabEdges.clear(); };

collab2Stmt: EDGEOP ID '.' method  
	{ collabEdges.add($method.text); collabEdges.add($ID.text); };

classDiagram 
	@init { graph = classFactory.createGraph();  }
	@after { graph.accept(new NodeRefVisitor()); }
	: 'class' 'diagram'? ID '{' (classStmt | interfaceStmt)* '}' { graph.setName($ID.text); };

classStmt:	'class' id=ID 
	('extends' eid+=ID (',' eid+=ID)*)* ('implements' eid+=ID (',' eid+=ID)*)* 
	'{' classElementStmt* '}'
	{ classFactory.createClassNode(graph, $id, curElements, $eid); curElements.clear(); }; 

interfaceStmt:	'interface' id=ID 
	('extends' eid+=ID (',' eid+=ID)*)* ('implements' eid+=ID (',' eid+=ID)*)* 
	'{' classElementStmt* '}'  
	{ classFactory.createInterfaceNode(graph, $id, curElements, $eid); curElements.clear(); }; 

classElementStmt: varClassElementStmt | staticVarClassElementStmt 
	| methodClassElementStmt | staticMethodClassElementStmt;

varClassElementStmt: ID ';' 
	{ curElements.add(classFactory.createNodeElement(UMLMetaType.CLASS_VAR_NODE_LABEL, $ID.text)); };

staticVarClassElementStmt: 'static' ID ';' 
	{ curElements.add(classFactory.createNodeElement(UMLMetaType.CLASS_STATIC_VAR_NODE_LABEL, $ID.text)); };

methodClassElementStmt: method ';' 
	{ curElements.add(classFactory.createNodeElement(UMLMetaType.CLASS_METHOD_NODE_LABEL, $method.text)); };

staticMethodClassElementStmt: 'static' method ';' 
	{ curElements.add(classFactory.createNodeElement(UMLMetaType.CLASS_STATIC_METHOD_NODE_LABEL, $method.text)); };

method: ID '(' (ID (',' ID)*)? ')';

EDGEOP: '->';
ID: ('_' | 'a'..'z' | 'A'..'Z' | ':') (INT | '_' | 'a'..'z' |'A'..'Z' | ':' | '[' | ']')*;
fragment INT : '0'..'9'+ ;
WS: (' ' | '\t' | NEWLINE)+ { skip(); };
fragment NEWLINE:'\r'? '\n';
