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
import org.modsl.core.Utils;
import org.modsl.core.agt.common.MetaTypeConfig;
import org.modsl.core.agt.model.Edge;
import org.modsl.core.agt.model.MetaType;
import org.modsl.core.agt.model.Node;
import org.modsl.core.agt.visitor.StringTemplateVisitor;

public class VisitorSTTest {

	protected Logger log = Logger.getLogger(getClass());

	protected static final String ROOT = "test/st/visitor";
	protected static final String STGDIRS = ROOT;

	protected StringTemplateGroup group;

	@Test
	public void graph() {

		Node<VMetaType> root = buildTree();
		StringTemplateVisitor<VMetaType> stv = new StringTemplateVisitor<VMetaType>(STGDIRS, "visitor_demo", 0);
		root.accept(stv);
		String result = stv.toString();

		// log.debug(result);

		assertEquals(1, Utils.matchCount(result, "<graph"));
		assertEquals(1, Utils.matchCount(result, "graph_name"));
		assertEquals(1, Utils.matchCount(result, "</graph>"));
		assertEquals(2, Utils.matchCount(result, "e1"));
		assertEquals(1, Utils.matchCount(result, "es1"));
		assertEquals(2, Utils.matchCount(result, "n.?1"));
		assertEquals(2, Utils.matchCount(result, "n.?2"));
		assertEquals(2, Utils.matchCount(result, "n.?3"));
		assertEquals(7, Utils.matchCount(result, "</node>"));
		assertEquals(4, Utils.matchCount(result, "</edge>"));

	}

	private Node<VMetaType> buildTree() {

		Node<VMetaType> root = new Node<VMetaType>(VMetaType.GRAPH, "graph_name");

		Node<VMetaType> n1 = new Node<VMetaType>(VMetaType.NODE, "n1");
		Node<VMetaType> n2 = new Node<VMetaType>(VMetaType.NODE, "n2");
		Node<VMetaType> n3 = new Node<VMetaType>(VMetaType.NODE, "n3_subroot");
		Node<VMetaType> n4 = new Node<VMetaType>(VMetaType.NODE, "n4");

		root.add(n1);
		root.add(n2);
		root.add(n3);
		root.add(n4);

		root.addChild(new Edge<VMetaType>(VMetaType.EDGE, "e1.2", n1, n2));
		root.addChild(new Edge<VMetaType>(VMetaType.EDGE, "e1.3", n1, n3));
		root.addChild(new Edge<VMetaType>(VMetaType.EDGE, "e2.3", n2, n3));

		Node<VMetaType> ns1 = new Node<VMetaType>(VMetaType.NODE, "ns1");
		Node<VMetaType> ns2 = new Node<VMetaType>(VMetaType.NODE, "ns2");
		Node<VMetaType> ns3 = new Node<VMetaType>(VMetaType.NODE, "ns3");

		n3.add(ns1);
		n3.add(ns2);
		n3.add(ns3);

		n3.addChild(new Edge<VMetaType>(VMetaType.EDGE, "es1.2", ns1, ns2));

		return root;

	}

	public enum VMetaType implements MetaType {

		GRAPH, NODE, EDGE;

		protected MetaTypeConfig config = new MetaTypeConfig();

		@Override
		public MetaTypeConfig getConfig() {
			return config;
		}

	}

}
