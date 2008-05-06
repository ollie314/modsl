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

package org.modsl.core.agt.layout;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.modsl.core.agt.model.AbstractAGTModelTest;
import org.modsl.core.agt.model.Edge;
import org.modsl.core.agt.model.Node;

public class SugiyamaLayoutTest extends AbstractAGTModelTest {

    protected SugiyamaLayout layout;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        layout = new SugiyamaLayout();
        layout.root = root;
    }

    @Test
    public void degreeSorting() {
        List<Node<?>> sorted = layout.sortByOutDegree();
        assertEquals(sorted.size(), root.getNodes().size());
        assertEquals(n1, sorted.get(0));
        assertEquals(n2, sorted.get(1));
        assertEquals(n5, sorted.get(sorted.size() - 1));
    }

    @Test
    public void layer() {
        layout.removeCycles();
        int h = layout.layer();
        assertEquals(4, h);
        assertEquals(1, n1.getLayer());
        assertEquals(1, n2.getLayer());
        assertEquals(2, n3.getLayer());
        assertEquals(2, n4.getLayer());
        assertEquals(3, n6.getLayer());
        assertEquals(3, n7.getLayer());
        assertEquals(4, n5.getLayer());
    }

    @Test
    public void removeCycles() {
        layout.removeCycles();
        assertFalse(e1_3.isReverted());
        assertFalse(e1_5.isReverted());
        assertFalse(e2_5.isReverted());
        assertFalse(e2_4.isReverted());
        assertFalse(e3_7.isReverted());
        assertFalse(e4_6.isReverted());
        assertTrue(e6_2.isReverted());
        assertFalse(e7_5.isReverted());
    }

    @Test
    public void sources() {
        assertEquals(1, layout.sources().size());
    }

    @Test
    public void split() {
        int sn = root.getNodes().size();
        int se = root.getChildEdges().size();
        // n1 --e1_5--> n5
        layout.split(e1_5);
        assertEquals(sn + 1, root.getNodes().size());
        assertEquals(se + 1, root.getChildEdges().size());
        // n1 --de--> dn --e1_5--> n5
        Node<?> dn = root.getNode(sn);
        assertEquals(e1_5, dn.getOutEdges().get(0));
        Edge<?> de = root.getChildEdge(se);
        assertEquals(de, dn.getInEdges().get(0));
        assertEquals(n1, de.getNode1());
        assertEquals(dn, de.getNode2());
        assertEquals(dn, e1_5.getNode1());
        assertEquals(n5, e1_5.getNode2());
    }

    @Test
    public void topologicalSort() {
        layout.removeCycles();
        List<Node<?>> sorted = layout.topologicalSort();
        assertEquals(root.getNodes().size(), sorted.size());
        assertEquals(n1, sorted.get(0));
        assertEquals(n2, sorted.get(1));
        assertEquals(n3, sorted.get(2));
        assertEquals(n4, sorted.get(3));
        assertEquals(n7, sorted.get(4));
        assertEquals(n6, sorted.get(5));
        assertEquals(n5, sorted.get(6));
    }

    @Test
    public void undoRemoveCycles() {
        layout.removeCycles();
        layout.undoRemoveCycles();
        assertFalse(e1_3.isReverted());
        assertFalse(e1_5.isReverted());
        assertFalse(e2_5.isReverted());
        assertFalse(e2_4.isReverted());
        assertFalse(e3_7.isReverted());
        assertFalse(e4_6.isReverted());
        assertFalse(e6_2.isReverted());
        assertFalse(e7_5.isReverted());
    }

    @Test
    public void insertDummies() {
        int sn = root.getNodes().size();
        int se = root.getChildEdges().size();
        layout.removeCycles();
        layout.layer();
        layout.insertDummies();
        //log.debug(new ToStringVisitor().toString(root));
        assertEquals(sn + 5, root.getNodes().size());
        assertEquals(se + 5, root.getChildEdges().size());
        assertNotNull(n1.getConnnectedEdgeTo(root.getNode("dummyNode1")));
        assertNotNull(root.getNode("dummyNode1").getConnnectedEdgeTo(root.getNode("dummyNode3")));
        assertNotNull(root.getNode("dummyNode3").getConnnectedEdgeTo(n5));
        assertNotNull(n2.getConnnectedEdgeTo(root.getNode("dummyNode5")));
        assertNotNull(root.getNode("dummyNode5").getConnnectedEdgeTo(root.getNode("dummyNode7")));
        assertNotNull(root.getNode("dummyNode7").getConnnectedEdgeTo(n5));
        assertNotNull(n2.getConnnectedEdgeTo(root.getNode("dummyNode9")));
        assertNotNull(root.getNode("dummyNode9").getConnnectedEdgeTo(n6));
    }

    @Test
    public void layerNodes() {
        layout.removeCycles();
        layout.layer();
        layout.insertDummies();
        assertEquals(2, layout.layerNodes(1).size());
        assertTrue(layout.layerNodes(1).contains(n1));
        assertTrue(layout.layerNodes(1).contains(n2));
        assertEquals(5, layout.layerNodes(2).size());
        assertTrue(layout.layerNodes(2).contains(n3));
        assertTrue(layout.layerNodes(2).contains(n4));
        assertTrue(layout.layerNodes(2).contains(root.getNode("dummyNode1")));
        assertTrue(layout.layerNodes(2).contains(root.getNode("dummyNode5")));
        assertTrue(layout.layerNodes(2).contains(root.getNode("dummyNode9")));
        assertEquals(4, layout.layerNodes(3).size());
        assertTrue(layout.layerNodes(3).contains(root.getNode("dummyNode3")));
        assertTrue(layout.layerNodes(3).contains(root.getNode("dummyNode7")));
        assertEquals(1, layout.layerNodes(4).size());
    }

}

/*


null:GRAPH {
    e1_3:EDGE(n1->n3)
    e2_4:EDGE(n2->n4)
    e3_7:EDGE(n3->n7)
    e4_6:EDGE(n4->n6)
    e1_5:EDGE(dummyNode3->n5)
    e2_5:EDGE(dummyNode7->n5)
    e6_2:EDGE(dummyNode9->n6)
    e7_5:EDGE(n7->n5)
    dummyEdge2:EDGE(n1->dummyNode1)
    dummyEdge4:EDGE(dummyNode1->dummyNode3)
    dummyEdge6:EDGE(n2->dummyNode5)
    dummyEdge8:EDGE(dummyNode5->dummyNode7)
    dummyEdge10:EDGE(n2->dummyNode9)
    n1:NODE
    n2:NODE
    n3:NODE
    n4:NODE
    n5:NODE
    n6:NODE
    n7:NODE
    dummyNode1:NODE
    dummyNode3:NODE
    dummyNode5:NODE
    dummyNode7:NODE
    dummyNode9:NODE
  }

*/