package org.modsl.antlr.dot;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;

public abstract class AbstractDotTest {

    public AbstractDotTest() {
        super();
    }

    protected TokensNTree parse(String s) throws RecognitionException {
        TokensNTree tnt = new TokensNTree();
        ANTLRStringStream input = new ANTLRStringStream(s);
        DotASTLexer lexer = new DotASTLexer(input);
        tnt.tokens = new CommonTokenStream(lexer);
        DotASTParser parser = new DotASTParser(tnt.tokens);
        DotASTParser.dotGraph_return r = parser.dotGraph();
        tnt.tree = (CommonTree) r.getTree();
        return tnt;
    }

    protected static class TokensNTree {
        public CommonTokenStream tokens;
        public CommonTree tree;
    }

}