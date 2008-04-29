package org.modsl.core.lang.uml;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.RecognitionException;
import org.modsl.antlr.uml.UMLLexer;
import org.modsl.antlr.uml.UMLParser;
import org.modsl.core.agt.model.Node;
import org.modsl.core.cfg.AbstractConfigLoader;
import org.modsl.core.cfg.AbstractProcessor;

public abstract class AbstractUMLProcessor extends AbstractProcessor<UMLMetaType, UMLParser> {

	public AbstractUMLProcessor() {
		super();
	}

	@Override
	protected AbstractConfigLoader getConfigLoader() {
		return new UMLConfigLoader(getPath(), getName(), UMLMetaType.class);
	}

	@Override
	protected Lexer getLexer(ANTLRStringStream input) {
		return new UMLLexer(input);
	}

	@Override
	protected UMLParser getParser(CommonTokenStream tokens) {
		return new UMLParser(tokens);
	}

	@Override
	protected String getPath() {
		return "cfg/uml:cfg";
	}

	@Override
	protected Node<UMLMetaType> getRoot() {
		return parser.root;
	}
	
	@Override
	protected void runParser() throws RecognitionException {
		parser.collabDiagram();
	}



}