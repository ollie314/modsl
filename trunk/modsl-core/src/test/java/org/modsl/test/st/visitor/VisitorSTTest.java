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

package org.modsl.test.st.visitor;

import static org.junit.Assert.assertEquals;

import org.antlr.stringtemplate.StringTemplateGroup;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.modsl.core.agt.model.MetaType;
import org.modsl.core.agt.model.Edge;
import org.modsl.core.agt.model.Node;
import org.modsl.core.agt.visitor.STVisitor;
import org.modsl.utils.Utils;

public class VisitorSTTest {

    protected Logger log = Logger.getLogger(getClass());

    protected static final String ROOT = "test/st/visitor";
    protected static final String STGDIRS = ROOT;

    protected StringTemplateGroup group;

    @Test
    public void graph() {

        Node<VType> root = buildTree();
        STVisitor<VType> stv = new STVisitor<VType>(STGDIRS, "visitor_demo", 0);
        root.accept(stv);
        String result = stv.toString();
        
        //log.debug(result);
        
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


    private Node<VType> buildTree() {

        Node<VType> root = new Node<VType>(VType.GRAPH, "graph_name");

        Node<VType> n1 = new Node<VType>(VType.NODE, "n1");
        Node<VType> n2 = new Node<VType>(VType.NODE, "n2");
        Node<VType> n3 = new Node<VType>(VType.NODE, "n3_subroot");
        Node<VType> n4 = new Node<VType>(VType.NODE, "n4");

        root.add(n1);
        root.add(n2);
        root.add(n3);
        root.add(n4);

        root.add(new Edge<VType>(VType.EDGE, "e1.2", n1, n2));
        root.add(new Edge<VType>(VType.EDGE, "e1.3", n1, n3));
        root.add(new Edge<VType>(VType.EDGE, "e2.3", n2, n3));

        Node<VType> ns1 = new Node<VType>(VType.NODE, "ns1");
        Node<VType> ns2 = new Node<VType>(VType.NODE, "ns2");
        Node<VType> ns3 = new Node<VType>(VType.NODE, "ns3");

        n3.add(ns1);
        n3.add(ns2);
        n3.add(ns3);

        n3.add(new Edge<VType>(VType.EDGE, "es1.2", ns1, ns2));

        return root;

    }

    public enum VType implements MetaType {
        GRAPH, NODE, EDGE;
    }

}
