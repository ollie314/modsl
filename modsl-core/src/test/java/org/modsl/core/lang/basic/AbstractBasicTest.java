package org.modsl.core.lang.basic;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.apache.log4j.Logger;
import org.modsl.antlr.basic.BasicLexer;
import org.modsl.antlr.basic.BasicParser;
import org.modsl.core.agt.model.Node;

public abstract class AbstractBasicTest {

	protected final Logger log = Logger.getLogger(getClass());

	protected Node<BasicType> parse(String s) throws RecognitionException {
		ANTLRStringStream input = new ANTLRStringStream(s);
		BasicLexer lexer = new BasicLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		BasicParser parser = new BasicParser(tokens);
		parser.graph();
		return parser.root;
	}

}