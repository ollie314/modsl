package org.modsl.core.lang.basic;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.RecognitionException;
import org.modsl.antlr.basic.BasicLexer;
import org.modsl.antlr.basic.BasicParser;
import org.modsl.core.agt.model.Node;
import org.modsl.core.cfg.AbstractProcessor;
import org.modsl.core.cfg.AbstractConfigLoader;

public class BasicProcessor extends AbstractProcessor<BasicMetaType, BasicParser> {

	@Override
	protected AbstractConfigLoader getConfigLoader() {
		return new BasicConfigLoader(getPath(), getName(), BasicMetaType.class);
	}

	@Override
	protected Lexer getLexer(ANTLRStringStream input) {
		return new BasicLexer(input);
	}

	@Override
	protected String getName() {
		return "basic";
	}

	@Override
	protected BasicParser getParser(CommonTokenStream tokens) {
		return new BasicParser(tokens);
	}

	@Override
	protected String getPath() {
		return "cfg/basic:cfg";
	}

	@Override
	protected Node<BasicMetaType> getRoot() {
		return parser.root;
	}

	@Override
	protected void runParser() throws RecognitionException {
		parser.graph();
	}

}
