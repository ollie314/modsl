package org.modsl.antlr.expr;

import java.io.IOException;

import junit.framework.TestCase;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

public class AntlrTestCase extends TestCase {

	public void testExpr() throws IOException, RecognitionException {
		ANTLRStringStream input = new ANTLRStringStream("\nx=1\nx+3\n");
		ExprLexer lexer = new ExprLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ExprParser parser = new ExprParser(tokens);
		parser.prog();
	}
}
