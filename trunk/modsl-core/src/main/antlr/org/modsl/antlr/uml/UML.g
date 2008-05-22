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
	protected LinkedList<String> curAggs = new LinkedList<String>();
}

diagram : classDiagram | collabDiagram;

collabDiagram 
	@init { graph = collabFactory.createGraph();  }
	@after { graph.accept(new NodeRefVisitor()); }
	: ('collab' | 'collaboration' | 'communication') 'diagram'? ID '{' collabStmt* '}' { graph.setName($ID.text); };

collabStmt: objInstance collab2Stmt+ ';'  
	{ collabEdges.addFirst($objInstance.text); collabFactory.createEdges(graph, collabEdges); collabEdges.clear(); };

collab2Stmt: EDGEOP objInstance '.' method  
	{ collabEdges.add($method.text); collabEdges.add($objInstance.text); };

classDiagram 
	@init { graph = classFactory.createGraph();  }
	@after { graph.accept(new NodeRefVisitor()); }
	: 'class' 'diagram'? ID '{' (classStmt | interfaceStmt)* '}' { graph.setName($ID.text); };

classStmt: 'class' id=ID 
	('extends' eid+=ID (',' eid+=ID)*)? ('implements' iid+=ID (',' iid+=ID)*)? 
	'{' classElementStmt* '}'
	{ 	
		classFactory.createClassNode(graph, $id, curElements, $eid, $iid, curAggs); 
		curElements.clear(); 
		curAggs.clear(); 
	}; 

interfaceStmt: 'interface' id=ID ('extends' eid+=ID (',' eid+=ID)*)? '{' interfaceElementStmt* '}'  
	{ classFactory.createInterfaceNode(graph, $id, curElements, $eid); curElements.clear(); }; 

classElementStmt: varClassElementStmt | staticVarClassElementStmt 
	| methodClassElementStmt | staticMethodClassElementStmt | aggStmt;

interfaceElementStmt: methodClassElementStmt | staticMethodClassElementStmt;

varClassElementStmt: var ';' 
	{ curElements.add(classFactory.createNodeElement(UMLMetaType.CLASS_VAR_NODE_LABEL, $var.text)); };

staticVarClassElementStmt: 'static' var ';' 
	{ curElements.add(classFactory.createNodeElement(UMLMetaType.CLASS_STATIC_VAR_NODE_LABEL, $var.text)); };

methodClassElementStmt: method ';' 
	{ curElements.add(classFactory.createNodeElement(UMLMetaType.CLASS_METHOD_NODE_LABEL, $method.text)); };

staticMethodClassElementStmt: 'static' method ';' 
	{ curElements.add(classFactory.createNodeElement(UMLMetaType.CLASS_STATIC_METHOD_NODE_LABEL, $method.text)); };

aggStmt: from=multiplicity EDGEOP to=multiplicity '(' ID ')' ';'
	{ curAggs.add($from.text); curAggs.add($to.text); curAggs.add($ID.text); };

var: ID (':' ID)?;

objInstance: ID? ':' ID | ID;

method: ID '(' (ID (',' ID)*)? ')' (':' ID)?;

multiplicity: multibound ('..' multibound)?;

multibound: '*' | ID | INT;

EDGEOP: '->';
ID: ('_' | 'a'..'z' | 'A'..'Z') (INT | '_' | 'a'..'z' |'A'..'Z' | '[' | ']')*;
INT : '0'..'9'+ ;
WS: (' ' | '\t' | NEWLINE)+ { skip(); };
fragment NEWLINE:'\r'? '\n';
