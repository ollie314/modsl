package org.modsl.antlr;

import java.io.IOException;

import junit.framework.TestCase;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;

public class AbstractAntlrTestCase extends TestCase {

	protected CSVParser createParser(String testString) throws IOException {
		CharStream stream = new ANTLRStringStream(testString);
		CSVLexer lexer = new CSVLexer(stream);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		CSVParser parser = new CSVParser(tokens);
		return parser;
	}

}
