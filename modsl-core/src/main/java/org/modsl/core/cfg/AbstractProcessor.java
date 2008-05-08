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

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.Parser;
import org.antlr.runtime.RecognitionException;
import org.modsl.core.agt.model.Graph;
import org.modsl.core.agt.model.Pt;
import org.modsl.core.agt.visitor.AbstractVisitor;
import org.modsl.core.agt.visitor.LayoutVisitor;
import org.modsl.core.agt.visitor.StringTemplateVisitor;

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

    private StringTemplateVisitor stringTemplateVisitor;
    private LayoutVisitor layoutVisitor;
    private AbstractConfigLoader configLoader;

    protected Lexer lexer;
    protected S parser;

    /**
     * @return diagram-specific config loader
     */
    protected abstract AbstractConfigLoader getConfigLoader(String path, String name);

    /**
     * @return layout visitor. It is possible though not likely that subclasses
     * will need to override this.
     */
    protected AbstractVisitor getLayoutVisitor() {
        return layoutVisitor;
    }

    /**
     * @param input
     * @return diagram-specific lexer
     */
    protected abstract Lexer getLexer(ANTLRStringStream input);

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
     * @return extract graph root node from the parser
     */
    protected abstract Graph getGraph();

    /**
     * @return string template visitor (rendering engine). It is possible though
     * not likely that subclasses will need to override this.
     */
    protected AbstractVisitor getStringTemplateVisitor() {
        return stringTemplateVisitor;
    }

    /**
     * Call this method once to initialize the processor
     */
    public void init() {
        configLoader = getConfigLoader(getPath(), getName());
        configLoader.load();
        stringTemplateVisitor = new StringTemplateVisitor(getPath(), getName(), getRefreshInterval());
        layoutVisitor = new LayoutVisitor();
    }

    /**
     * @return template refresh interval
     */
    protected int getRefreshInterval() {
        return Integer.MAX_VALUE;
    }

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
     * Parse input and return rendered string result with rescaling to reqested
     * size
     * @param s input
     * @param reqSize requested diagram size
     * @return result
     * @throws RecognitionException
     */
    public String process(String s, Pt reqSize) throws RecognitionException {
        Graph graph = parse(s);
        graph.setReqSize(reqSize);
        graph.accept(getLayoutVisitor());
        graph.rescale(graph.getReqSize());
        graph.accept(getStringTemplateVisitor());
        return getStringTemplateVisitor().toString();
    }

    /**
     * Parse input and return rendered string result without rescaling
     * @param s input
     * @return result
     * @throws RecognitionException
     */
    public String process(String s) throws RecognitionException {
        Graph graph = parse(s);
        graph.accept(getLayoutVisitor());
        graph.accept(getStringTemplateVisitor());
        return getStringTemplateVisitor().toString();
    }

    /**
     * Run diagram-specific parser (usually a call to the root parser rule)
     * @throws RecognitionException
     */
    protected abstract void runParser() throws RecognitionException;

}
