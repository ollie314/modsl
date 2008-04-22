tree grammar DotWalker;

options {
	tokenVocab = DotAST;
	ASTLabelType = CommonTree;
	output = template;
}

@header {
package org.modsl.antlr.dot;
}

dotGraph: ^(ID statements+=statement*) -> graph(name={$ID}, statements={$statements});

statement: (nodeStatement | edgeStatement);

nodeStatement: ^(NODE ID attributeList?) -> node(name={$ID});

edgeStatement: ^(EDGE ids+=ID+ attributeList?) -> edge(name={$ids});

attributeList: attribute+;

attribute: ^(ATTRIBUTE key=ID value=ID) -> attribute(key={$key}, value={$value});



