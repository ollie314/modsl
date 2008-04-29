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

import java.util.HashMap;
import java.util.Map;

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
public abstract class AbstractProcessor<T extends MetaType, S extends Parser> {

    private Map<String, StringTemplateVisitor<T>> stringTemplateVisitorMap = new HashMap<String, StringTemplateVisitor<T>>();
    private LayoutVisitor<T> layoutVisitor;
    private AbstractConfigLoader configLoader;

    protected Lexer lexer;
    protected S parser;

    /**
     * @return diagram-specific config loader
     */
    protected abstract AbstractConfigLoader getConfigLoader(String path, String name);

    /**
     * @return layout visitor. It is possible though not likely that subclesses
     * will need to override this.
     */
    protected AbstractVisitor<T> getLayoutVisitor() {
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
     * @return template refresh interval
     */
    protected int getRefreshInterval() {
        return Integer.MAX_VALUE;
    }

    /**
     * @return extract diagram-specific root node from the parser
     */
    protected abstract Node<T> getRoot();

    protected AbstractVisitor<T> getStringTemplateVisitor(String name) {
        StringTemplateVisitor<T> stv = stringTemplateVisitorMap.get(name);
        if (stv == null) {
            stv = loadStringTemplateVisitor(name);
            stringTemplateVisitorMap.put(name, stv);
        }
        return stv;
    }

    /**
     * Resolve string template group name. Default implementation uses the root node's type (i.e. GRAPH -> GRAPH.stg).
     * @param root
     * @return template group name (w/o .stg)
     */
    protected String getTemplateGroupName(Node<T> root) {
        return root.getType().toString();
    }

    /**
     * Call this method once to initialize the processor
     */
    public void init() {
        configLoader = getConfigLoader(getPath(), getName());
        configLoader.load();
        layoutVisitor = new LayoutVisitor<T>();
    }

    /**
     * Loads string template visitor for the given name. It is possible though
     * not likely that subclasses will need to override this.
     * @param name template group name
     * @return string template visitor (rendering engine)
     */
    protected StringTemplateVisitor<T> loadStringTemplateVisitor(String name) {
        return new StringTemplateVisitor<T>(getPath(), name, getRefreshInterval());
    }

    /**
     * Parse input
     * @param s input
     * @return abstract graph tree's root node
     * @throws RecognitionException
     */
    public Node<T> parse(String s) throws RecognitionException {
        ANTLRStringStream input = new ANTLRStringStream(s);
        this.lexer = getLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        this.parser = getParser(tokens);
        runParser();
        return getRoot();
    }

    /**
     * Parse input and return rendered string result
     * @param s input
     * @param reqSize requested diagram size
     * @return result
     * @throws RecognitionException
     */
    public String process(String s, Pt reqSize) throws RecognitionException {
        Node<T> root = parse(s);
        root.setReqSize(reqSize);
        root.accept(getLayoutVisitor());
        root.rescale(root.getReqSize());
        AbstractVisitor<T> stv = getStringTemplateVisitor(getTemplateGroupName(root));
        root.accept(stv);
        return stv.toString();
    }

    /**
     * Run diagram-specific parser (usually a call to the root parser rule)
     * @throws RecognitionException
     */
    protected abstract void runParser() throws RecognitionException;

}
