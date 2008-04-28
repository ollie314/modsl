package org.modsl.core.lang.basic;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.Parser;
import org.antlr.runtime.RecognitionException;
import org.modsl.antlr.basic.BasicLexer;
import org.modsl.antlr.basic.BasicParser;
import org.modsl.core.agt.model.Node;
import org.modsl.core.cfg.AbstractProcessor;

public class BasicProcessor extends AbstractProcessor {

    @Override
    protected Lexer getLexer(ANTLRStringStream input) {
        return new BasicLexer(input);
    }

    @Override
    protected Parser getParser(CommonTokenStream tokens) {
        return new BasicParser(tokens);
    }

    @Override
    protected Node<?> getRoot(Parser parser) {
        BasicParser bp = (BasicParser) parser;
        return bp.root;
    }

    @Override
    protected void runParser(Parser parser) throws RecognitionException {
        BasicParser bp = (BasicParser) parser;
        bp.graph();
    }

}
