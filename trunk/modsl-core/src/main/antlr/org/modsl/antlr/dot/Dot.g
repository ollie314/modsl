grammar Dot;

options {
	language = Java;
}

@lexer::header {
	package org.modsl.antlr.dot;
}

@parser::header {
	package org.modsl.antlr.dot;
	import org.modsl.core.agt.model.*;
	import org.modsl.core.agt.visitor.*;
	import org.modsl.core.lang.dot.*;
	import java.util.Deque;
	import java.util.LinkedList;
}

@parser::members {
	public Graph graph;
	protected DotFactory factory = new DotFactory();
}

graph 
	@init{ graph = factory.createGraph();  }
	@after { graph.accept(new NodeRefVisitor()); }
	: 'graph' ID '{' statement* '}' { graph.setName($ID.text); };

statement: (nodeStatement | edgeStatement) ';';

nodeStatement : ID attributeList? { Node n = factory.createNode(graph, $ID);  };

edgeStatement: ids+=ID EDGEOP ids+=ID (EDGEOP ids+=ID)* attributeList? { factory.createEdges(graph, $ids); };

attributeList: '[' attribute (',' attribute)* ']';

attribute: key=ID '=' value=ID; // ignored for now

EDGEOP: '->' | '--';
ID: ('"' .* '"' |  ('_' | 'a'..'z' |'A'..'Z' ) (INT | '_' | 'a'..'z' |'A'..'Z')* | INT);
fragment INT : '0'..'9'+ ;
NEWLINE:'\r'? '\n';
WS: (' ' |'\t' | '\r' | '\n')+ { skip(); };