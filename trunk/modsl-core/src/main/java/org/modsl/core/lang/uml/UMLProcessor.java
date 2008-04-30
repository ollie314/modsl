/**
 * Copyright 2008 Andrew Vishnyakov <avishn@gmail.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */

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

public class UMLProcessor extends AbstractProcessor<UMLMetaType, UMLParser> {

	@Override
	protected AbstractConfigLoader getConfigLoader(String path, String name) {
		return new UMLConfigLoader(path, name, UMLMetaType.class);
	}

	@Override
	protected Lexer getLexer(ANTLRStringStream input) {
		return new UMLLexer(input);
	}

	@Override
	protected String getName() {
		return "uml";
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
