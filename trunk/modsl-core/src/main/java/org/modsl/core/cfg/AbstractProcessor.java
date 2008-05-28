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

package org.modsl.core.cfg;

import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.Parser;
import org.antlr.runtime.RecognitionException;
import org.modsl.core.agt.layout.AbstractLayoutVisitor;
import org.modsl.core.agt.model.Graph;
import org.modsl.core.agt.model.MetaType;
import org.modsl.core.agt.model.Pt;
import org.modsl.core.agt.render.AbstractRenderVisitor;
import org.modsl.core.agt.render.Java2DRenderVisitor;
import org.modsl.core.agt.render.StringTemplateVisitor;
import org.modsl.core.agt.visitor.AbstractVisitor;

/**
 * Combines all individual processing steps into a single unit. Individual
 * diagram types are supposed to extend this class and implement callback
 * methods.
 * 
 * @author AVishnyakov
 * 
 * @param <T> meta type class
 * @param <S> parser class
 */
public abstract class AbstractProcessor<S extends Parser> {

	private List<AbstractLayoutVisitor> layoutVisitors = new LinkedList<AbstractLayoutVisitor>();

	protected Lexer lexer;
	protected S parser;

	protected void addLayoutVisitor(AbstractLayoutVisitor layoutVisitor) {
		layoutVisitors.add(layoutVisitor);
	}

	/**
	 * @return extract graph root node from the parser
	 */
	protected abstract Graph getGraph();

	/**
	 * @param input
	 * @return diagram-specific lexer
	 */
	protected abstract Lexer getLexer(ANTLRStringStream input);

	/**
	 * @return meta type class
	 */
	protected abstract Class<? extends MetaType> getMetaTypeClass();

	/**
	 * @return diagram type name (used to find the config properties)
	 */
	protected abstract String getName();

	/**
	 * @param tokens
	 * @return diagram-specific parser
	 */
	protected abstract S getParser(CommonTokenStream tokens);

	/**
	 * @return config path (colon-separated) (used to find the config
	 * properties)
	 */
	protected abstract String getPath();

	/**
	 * @return template refresh interval
	 */
	protected int getRefreshInterval() {
		return Integer.MAX_VALUE;
	}

	/**
	 * @return string template visitor (rendering engine). It is possible though
	 * not likely that subclasses will need to override this.
	 */
	protected AbstractVisitor getStringTemplateVisitor() {
		return new StringTemplateVisitor(getPath(), getName(), getRefreshInterval());
	}

	/**
	 * Call this method once to initialize the processor
	 */
	public void init() {
		initLayouts();
		initDecorators();
		for (AbstractLayoutVisitor lv : layoutVisitors) {
			if (lv.getConfigName() != null) {
				PropLoader pl = new PropLoader(getPath(), lv.getConfigName(), false);
				pl.load();
				lv.setLayoutConfig(pl.getProps());
			}
		}
		new FontTransformLoader(getPath(), getName(), getMetaTypeClass()).load();
	}

	/**
	 * Initialize layout classes. Subclasses of this class need to register
	 * layout manager class arrays with corresponding meta type class instances.
	 */
	protected abstract void initDecorators();

	/**
	 * Initialize layout classes
	 */
	protected abstract void initLayouts();

	/**
	 * Parse input
	 * @param s input
	 * @return abstract graph tree's root node
	 * @throws RecognitionException
	 */
	public Graph parse(String s) throws RecognitionException {
		ANTLRStringStream input = new ANTLRStringStream(s);
		this.lexer = getLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		this.parser = getParser(tokens);
		runParser();
		return getGraph();
	}

	/**
	 * Parse input and return rendered string result without rescaling
	 * @param s input
	 * @return result
	 * @throws RecognitionException
	 */
	public String process(String s) throws RecognitionException {
		Graph graph = parse(s);
		for (AbstractLayoutVisitor layout : layoutVisitors) {
			graph.accept(layout);
		}
		graph.rescale();
		AbstractVisitor stv = getStringTemplateVisitor();
		graph.accept(stv);
		return stv.toString();
	}

	/**
	 * Parse input and return rendered PNG image result without rescaling
	 * @param s input
	 * @return result
	 * @throws RecognitionException
	 */
	public AbstractRenderVisitor processToPng(String s) throws RecognitionException {
		Graph graph = parse(s);
		for (AbstractLayoutVisitor layout : layoutVisitors) {
			graph.accept(layout);
		}
		graph.rescale();
		AbstractRenderVisitor stv = getRenderVisitor();
		graph.accept(stv);
		return stv;
	}

	private AbstractRenderVisitor getRenderVisitor() {
		return new Java2DRenderVisitor();// getPath(), getName(),
		// getRefreshInterval());
	}

	/**
	 * Parse input and return rendered string result with rescaling to reqested
	 * size
	 * @param s input
	 * @param reqSize requested diagram size
	 * @return result
	 * @throws RecognitionException
	 */
	public String process(String s, Pt reqSize) throws RecognitionException {
		Graph graph = parse(s);
		graph.setReqSize(reqSize.x, reqSize.y);
		for (AbstractLayoutVisitor layout : layoutVisitors) {
			graph.accept(layout);
		}
		graph.rescale(graph.getReqSize());
		AbstractVisitor stv = getStringTemplateVisitor();
		graph.accept(stv);
		return stv.toString();
	}

	/**
	 * Run diagram-specific parser (usually a call to the root parser rule)
	 * @throws RecognitionException
	 */
	protected abstract void runParser() throws RecognitionException;

}
