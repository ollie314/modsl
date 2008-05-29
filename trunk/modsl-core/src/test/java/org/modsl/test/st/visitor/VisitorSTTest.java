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

package org.modsl.test.st.visitor;

import static org.junit.Assert.assertEquals;

import org.antlr.stringtemplate.StringTemplateGroup;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.modsl.core.agt.common.MetaTypeConfig;
import org.modsl.core.agt.model.Edge;
import org.modsl.core.agt.model.Graph;
import org.modsl.core.agt.model.MetaType;
import org.modsl.core.agt.model.Node;
import org.modsl.core.agt.render.StringTemplateVisitor;
import org.modsl.core.render.Style;
import org.modsl.core.utils.Utils;

public class VisitorSTTest {

	public enum VMetaType implements MetaType {

		GRAPH, NODE, EDGE;

		protected MetaTypeConfig config = new MetaTypeConfig();

		Style style;
	    public MetaTypeConfig getConfig() {
			return config;
		}

	    public Style getStyle() {
	        return style;
	    }

	    public void setStyle(Style style) {
	        this.style = style;
	    }


	}

	protected static final String ROOT = "test/st/visitor";
	protected static final String STGDIRS = ROOT;

	protected Logger log = Logger.getLogger(getClass());

	protected StringTemplateGroup group;

	private Graph buildTree() {

		Graph graph = new Graph(VMetaType.GRAPH, "graph_name");

		Node n1 = new Node(VMetaType.NODE, "n1");
		Node n2 = new Node(VMetaType.NODE, "n2");
		Node n3 = new Node(VMetaType.NODE, "n3");

		graph.add(n1);
		graph.add(n2);
		graph.add(n3);

		graph.add(new Edge(VMetaType.EDGE, "e1_2", n1, n2));

		return graph;

	}

	@Test
    public void graph() {

        Graph graph = buildTree();
        // log.debug(new ToStringVisitor().toString(graph));
        StringTemplateVisitor stv = new StringTemplateVisitor(STGDIRS, "visitor_demo", 0);
        graph.accept(stv);
        String result = stv.toString();

        // log.debug(result);

        assertEquals(1, Utils.matchCount(result, "<graph"));
        assertEquals(1, Utils.matchCount(result, "graph_name"));
        assertEquals(1, Utils.matchCount(result, "</graph>"));
        assertEquals(1, Utils.matchCount(result, "e1_2"));
        assertEquals(1, Utils.matchCount(result, "n1"));
        assertEquals(1, Utils.matchCount(result, "n2"));
        assertEquals(1, Utils.matchCount(result, "n3"));
        assertEquals(3, Utils.matchCount(result, "</node>"));
        assertEquals(1, Utils.matchCount(result, "</edge>"));

    }

}
