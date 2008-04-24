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
import org.modsl.core.agt.model.AGTType;
import org.modsl.core.agt.model.Edge;
import org.modsl.core.agt.model.Node;
import org.modsl.st.STErrorListener;

public class STVisitor<T extends AGTType> extends AbstractVisitor<T> {

    private static final String SUFF_IN = "_in";
    private static final String SUFF_OUT = "_out";

    protected Logger log = Logger.getLogger(getClass());
    protected StringTemplateGroup group;

    protected StringBuilder sb = new StringBuilder();

    public STVisitor(String stgPath, String stgName, int refresh) {
        StringTemplateGroup.registerGroupLoader(new CommonGroupLoader(stgPath, new STErrorListener()));
        group = StringTemplateGroup.loadGroup(stgName, DefaultTemplateLexer.class, null);
        group.setRefreshInterval(refresh);
    }

    private void callTemplate(Edge<T> edge, String suff) {
        StringTemplate st = group.getInstanceOf(edge.getType() + suff);
        st.setAttribute("edge", edge);
        sb.append(st.toString());
    }

    private void callTemplate(Node<T> node, String suff) {
        StringTemplate st = group.getInstanceOf(node.getType() + suff);
        st.setAttribute("node", node);
        sb.append(st.toString());
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

    public String toString(Node<T> root) {
        root.accept(this);
        return toString();
    }

}
