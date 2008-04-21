tree grammar DotWalker;

options {
	tokenVocab = Dot;
	ASTLabelType = CommonTree;
}

@header {
package org.modsl.antlr.dot;
}

dotGraph: ^(ID statement*) ;

statement: (nodeStatement | edgeStatement);

nodeStatement: ^(NODE ID attributeList?);

edgeStatement: ^(EDGE ID+ attributeList?);

attributeList: attribute+;

attribute: ^(ATTRIBUTE key value);

key: ID;

value: ID;


