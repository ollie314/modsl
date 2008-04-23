package org.modsl.antlr.dot;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

public abstract class AbstractDotTest {

    public AbstractDotTest() {
        super();
    }

    protected void parse(String s) throws RecognitionException {
        ANTLRStringStream input = new ANTLRStringStream(s);
        DotLexer lexer = new DotLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        DotParser parser = new DotParser(tokens);
        parser.dotGraph();
    }

}