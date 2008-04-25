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

package org.modsl.core.agt.visitor;

import org.antlr.stringtemplate.CommonGroupLoader;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;
import org.apache.log4j.Logger;
import org.modsl.core.agt.model.MetaType;
import org.modsl.core.agt.model.Edge;
import org.modsl.core.agt.model.Node;
import org.modsl.st.STErrorListener;

/**
 * Renders abstract graph tree as a string using given string template 
 * 
 * @author avishnyakov
 *
 * @param <T>
 */
public class STVisitor<T extends MetaType> extends AbstractVisitor<T> {

    private static final String SUFF_IN = "_in";
    private static final String SUFF_OUT = "_out";

    protected Logger log = Logger.getLogger(getClass());
    protected StringTemplateGroup group;

    protected StringBuilder sb = new StringBuilder();

    /**
     * Create new
     * @param stgPath string template group path (multiple dirs separated by colon)
     * @param stgName string template group name (specific to given graph type)
     * @param refresh template refresh timeout, seconds
     */
    public STVisitor(String stgPath, String stgName, int refresh) {
        StringTemplateGroup.registerGroupLoader(new CommonGroupLoader(stgPath, new STErrorListener()));
        group = StringTemplateGroup.loadGroup(stgName, DefaultTemplateLexer.class, null);
        group.setRefreshInterval(refresh);
    }

    /**
     * Call template "edgeType"_suff (edgeType_in or edgeType_out) on the given edge
     * @param edge
     * @param suff _in or _out
     */
    private void callTemplate(Edge<T> edge, String suff) {
        sb.append(callTemplate(edge.getType() + suff, "edge", edge));
    }

    /**
     * Call template "nodeType"_suff (nodeType_in or nodeType_out) on the given node
     * @param node
     * @param suff _in or _out
     */
    private void callTemplate(Node<T> node, String suff) {
        sb.append(callTemplate(node.getType() + suff, "node", node));
    }

    /** 
     * Call template, ignore "template not found" error
     * @param name template name
     * @param key attribute key
     * @param value attribute value
     * @return resulting string
     */
    private String callTemplate(String name, String key, Object value) {
        try {
            StringTemplate st = group.getInstanceOf(name);
            if (st != null) {
                st.setAttribute(key, value);
                return st.toString();
            } else {
                return "";
            }
        } catch (IllegalArgumentException ex) {
            return "";
        }
    }

    @Override
    public void in(Edge<T> edge) {
        callTemplate(edge, SUFF_IN);
    }

    @Override
    public void in(Node<T> node) {
        callTemplate(node, SUFF_IN);
    }

    @Override
    public void out(Edge<T> edge) {
        callTemplate(edge, SUFF_OUT);
    }

    @Override
    public void out(Node<T> node) {
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
    public String toString(Node<T> root) {
        root.accept(this);
        return toString();
    }

}
