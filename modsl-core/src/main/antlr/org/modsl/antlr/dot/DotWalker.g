tree grammar DotWalker;

options {
	tokenVocab = DotAST;
	ASTLabelType = CommonTree;
}

@header {
package org.modsl.antlr.dot;
}

dotGraph: ^(ID statement*) { System.out.println("G " + $ID.text); };

statement: (nodeStatement | edgeStatement);

nodeStatement: ^(NODE ID attributeList?) { System.out.println("N " + $ID.text); };

edgeStatement: ^(EDGE ids+=ID+ attributeList?) { System.out.println("E " + $ids); };

attributeList: attribute+;

attribute: ^(ATTRIBUTE key value) { System.out.println("A " + $key.text + "=" + $value.text); };

key: ID;

value: ID;


