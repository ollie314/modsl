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

package org.modsl.core.agt.visitor;

import org.antlr.stringtemplate.CommonGroupLoader;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;
import org.apache.log4j.Logger;
import org.modsl.core.agt.decor.AbstractDecorator;
import org.modsl.core.agt.model.AbstractElement;
import org.modsl.core.agt.model.Edge;
import org.modsl.core.agt.model.Graph;
import org.modsl.core.agt.model.Label;
import org.modsl.core.agt.model.Node;

/**
 * Renders abstract graph tree as a string using given string template
 * 
 * @author avishnyakov
 * 
 * @param <T> meta type class
 */
public class StringTemplateVisitor extends AbstractVisitor {

    private static final String SUFF_IN = "_in";
    private static final String SUFF_OUT = "_out";

    protected Logger log = Logger.getLogger(getClass());
    protected StringTemplateGroup group;

    protected StringBuilder sb = new StringBuilder();

    /**
     * Create new
     * @param stgPath string template group path (multiple dirs separated by
     * colon)
     * @param stgName string template group name (specific to given graph type)
     * @param refresh template refresh timeout, seconds
     */
    public StringTemplateVisitor(String stgPath, String stgName, int refresh) {
        StringTemplateGroup.registerGroupLoader(new CommonGroupLoader(stgPath, new STErrorListener()));
        group = StringTemplateGroup.loadGroup(stgName, DefaultTemplateLexer.class, null);
        if (refresh > -1) {
            group.setRefreshInterval(refresh);
        }
    }

    /**
     * Call template "edgeType"_suff (edgeType_in or edgeType_out) on the given
     * edge
     * @param edge
     * @param suff _in or _out
     */
    private void callTemplate(Edge edge, String suff) {
        sb.append(callTemplate(edge.getType() + suff, "edge", edge));
    }

    /**
     * Call template "graphType"_suff (graphType_in or graphType_out) on the
     * given graph
     * @param graph
     * @param suff _in or _out
     */
    private void callTemplate(Graph graph, String suff) {
        sb.append(callTemplate(graph.getType() + suff, "graph", graph));
    }

    /**
     * Call template "labelType"_suff (labelType_in or labelType_out) on the
     * given label
     * @param label
     * @param suff _in or _out
     */
    private void callTemplate(Label label, String suff) {
        sb.append(callTemplate(label.getType() + suff, "label", label));
    }

    /**
     * Call template "nodeType"_suff (nodeType_in or nodeType_out) on the given
     * node
     * @param node
     * @param suff _in or _out
     */
    private void callTemplate(Node node, String suff) {
        sb.append(callTemplate(node.getType() + suff, "node", node));
    }

    /**
     * Call template, ignore "template not found" error
     * @param name template name
     * @param key element key
     * @param element element value
     * @return resulting string
     */
    private String callTemplate(String name, String key, AbstractElement<?> element) {
        try {
            StringTemplate st = group.getInstanceOf(name);
            if (st != null) {
                st.setAttribute(key, element);
                st.setAttribute("decor", getDecorator(element));
                return st.toString();
            } else {
                return "";
            }
        } catch (IllegalArgumentException ex) {
            return "";
        }
    }

    @SuppressWarnings("unchecked")
    private Object getDecorator(AbstractElement element) {
        AbstractDecorator ad = element.getType().getConfig().getDecorator();
        if (ad == null) {
            return null;
        } else {
            ad.decorate(element);
            return ad;
        }
    }

    @Override
    public void in(Edge edge) {
        callTemplate(edge, SUFF_IN);
    }

    @Override
    public void in(Graph graph) {
        callTemplate(graph, SUFF_IN);
    }

    @Override
    public void in(Label label) {
        callTemplate(label, SUFF_IN);
    }

    @Override
    public void in(Node node) {
        callTemplate(node, SUFF_IN);
    }

    @Override
    public void out(Edge edge) {
        callTemplate(edge, SUFF_OUT);
    }

    @Override
    public void out(Graph graph) {
        callTemplate(graph, SUFF_OUT);
    }

    @Override
    public void out(Label label) {
        callTemplate(label, SUFF_OUT);
    }

    @Override
    public void out(Node node) {
        callTemplate(node, SUFF_OUT);
    }

    @Override
    public String toString() {
        return sb.toString();
    }

    /**
     * Return given AGT as a string
     * @param root
     * @return string
     */
    public String toString(Node root) {
        root.accept(this);
        return toString();
    }

}
