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

package org.modsl.core.cfg;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.Parser;
import org.antlr.runtime.RecognitionException;
import org.modsl.core.agt.model.MetaType;
import org.modsl.core.agt.model.Node;
import org.modsl.core.agt.model.Pt;
import org.modsl.core.agt.visitor.AbstractVisitor;
import org.modsl.core.agt.visitor.LayoutVisitor;
import org.modsl.core.agt.visitor.StringTemplateVisitor;

public abstract class AbstractProcessor<T extends MetaType> {

	protected StringTemplateVisitor<T> stringTemplateVisitor;
	protected LayoutVisitor<T> layoutVisitor;

	private AbstractVisitor<T> getLayoutVisitor() {
		return layoutVisitor;
	}

	protected abstract Lexer getLexer(ANTLRStringStream input);

	protected abstract String getName();

	protected abstract Parser getParser(CommonTokenStream tokens);

	protected abstract String getPath();

	protected abstract Node<T> getRoot(Parser parser);

	private AbstractVisitor<T> getStringTemplateVisitor() {
		return stringTemplateVisitor;
	}

	public void init() {
		stringTemplateVisitor = new StringTemplateVisitor<T>(getPath(), getName(), 0);
		layoutVisitor = new LayoutVisitor<T>();
	}

	protected Node<T> parse(String s) throws RecognitionException {
		ANTLRStringStream input = new ANTLRStringStream(s);
		Lexer lexer = getLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		Parser parser = getParser(tokens);
		runParser(parser);
		return getRoot(parser);
	}

	public String process(String s, Pt reqSize) throws RecognitionException {
		Node<T> root = parse(s);
		root.setReqSize(reqSize);
		root.accept(getLayoutVisitor());
		root.rescale(root.getReqSize());
		root.accept(getStringTemplateVisitor());
		return getStringTemplateVisitor().toString();
	}

	protected abstract void runParser(Parser parser) throws RecognitionException;

}
