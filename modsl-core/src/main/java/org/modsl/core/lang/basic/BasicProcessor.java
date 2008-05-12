/**
 * Copyright 2008 Andrew Vishnyakov <avishn@gmail.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.modsl.core.lang.basic;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.RecognitionException;
import org.modsl.antlr.basic.BasicLexer;
import org.modsl.antlr.basic.BasicParser;
import org.modsl.core.agt.decor.MetaTypeMapDecorator;
import org.modsl.core.agt.layout.SimpleNodeLabelPosLayoutVisitor;
import org.modsl.core.agt.layout.SimpleNodeLabelSizeLayoutVisitor;
import org.modsl.core.agt.layout.fr2.Circle2LayoutVisitor;
import org.modsl.core.agt.layout.fr2.FR2LayoutVisitor;
import org.modsl.core.agt.model.Graph;
import org.modsl.core.agt.model.MetaType;
import org.modsl.core.cfg.AbstractProcessor;

public class BasicProcessor extends AbstractProcessor<BasicParser> {

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
	protected Graph getGraph() {
		return parser.graph;
	}

	@Override
	protected void runParser() throws RecognitionException {
		parser.graph();
	}

	@Override
	public void initLayouts() {
		addLayoutVisitor(new SimpleNodeLabelSizeLayoutVisitor(BasicMetaType.NODE));
		addLayoutVisitor(new Circle2LayoutVisitor(BasicMetaType.GRAPH));
		addLayoutVisitor(new FR2LayoutVisitor(BasicMetaType.GRAPH));
		addLayoutVisitor(new SimpleNodeLabelPosLayoutVisitor(BasicMetaType.NODE));
	}

	@Override
	public void initDecorators() {
		BasicMetaType.GRAPH.getConfig().setDecorator(new MetaTypeMapDecorator(BasicMetaType.class));
	}

	@Override
	protected Class<? extends MetaType> getMetaTypeClass() {
		return BasicMetaType.class;
	}
}
