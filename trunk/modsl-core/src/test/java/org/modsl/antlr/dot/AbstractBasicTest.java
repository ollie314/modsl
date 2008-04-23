package org.modsl.antlr.dot;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.modsl.core.agt.DotType;
import org.modsl.core.agt.Node;

public abstract class AbstractBasicTest {

    public AbstractBasicTest() {
        super();
    }

    protected Node<DotType> parse(String s) throws RecognitionException {
        ANTLRStringStream input = new ANTLRStringStream(s);
        DotLexer lexer = new DotLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        DotParser parser = new DotParser(tokens);
        parser.dotGraph();
        return parser.root;
    }

}