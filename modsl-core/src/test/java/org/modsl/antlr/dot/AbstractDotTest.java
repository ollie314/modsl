package org.modsl.antlr.dot;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.modsl.agt.Node;

public abstract class AbstractDotTest {

    public AbstractDotTest() {
        super();
    }

    protected Node parse(String s) throws RecognitionException {
        ANTLRStringStream input = new ANTLRStringStream(s);
        DotLexer lexer = new DotLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        DotParser parser = new DotParser(tokens);
        parser.dotGraph();
        return parser.root;
    }

}