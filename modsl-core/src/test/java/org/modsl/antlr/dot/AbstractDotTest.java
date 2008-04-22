package org.modsl.antlr.dot;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;

public class AbstractDotTest {

    public AbstractDotTest() {
        super();
    }

    protected CommonTree parse(String s) throws RecognitionException {
    	ANTLRStringStream input = new ANTLRStringStream(s);
    	DotASTLexer lexer = new DotASTLexer(input);
    	CommonTokenStream tokens = new CommonTokenStream(lexer);
    	DotASTParser parser = new DotASTParser(tokens);
    	DotASTParser.dotGraph_return r = parser.dotGraph();
    	return (CommonTree) r.getTree();
    }

}