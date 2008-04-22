tree grammar DotWalker;

options {
	tokenVocab = DotAST;
	ASTLabelType = CommonTree;
	output = template;
}

@header {
package org.modsl.antlr.dot;
}

dotGraph: ^(ID statements+=statement*) -> graph(name={$ID.text}, statements={$statements});

statement: (nodeStatement | edgeStatement);

nodeStatement: ^(NODE ID attributeList?) -> node(name={$ID.text});

edgeStatement: ^(EDGE ids+=ID+ attributeList?) -> edge(name={$ids});

attributeList: attribute+;

attribute: ^(ATTRIBUTE key=ID value=ID) -> attribute(key={$key.text}, value={$value.text});



