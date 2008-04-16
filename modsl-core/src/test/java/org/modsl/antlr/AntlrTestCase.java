package org.modsl.antlr;

import java.io.IOException;

import junit.framework.TestCase;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

public class AntlrTestCase extends TestCase {

	public void testExpr() throws IOException, RecognitionException {
		ANTLRInputStream input = new ANTLRInputStream(System.in);
		ExprLexer lexer = new ExprLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ExprParser parser = new ExprParser(tokens);
		parser.prog();
	}
}
