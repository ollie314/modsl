package org.modsl.antlr;

import java.io.IOException;

import junit.framework.TestCase;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;

public abstract class AbstractModSLAntlrTestCase extends TestCase {

	protected ModSLParser createParser(String testString) throws IOException {
		CharStream stream = new ANTLRStringStream(testString);
		ModSLLexer lexer = new ModSLLexer(stream);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ModSLParser parser = new ModSLParser(tokens);
		return parser;
	}

}
