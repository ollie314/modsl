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
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.modsl.core.agt.model.AbstractAGTModelTest;
import org.modsl.core.agt.model.Edge;
import org.modsl.core.agt.model.Node;

public class SugiyamaLayoutTest extends AbstractAGTModelTest {

    protected SugiyamaLayout layout = new SugiyamaLayout();

    @Test
    public void degreeSorting() {
        List<Node<?>> sorted = layout.sortByOutDegree(root);
        assertEquals(sorted.size(), root.getNodes().size());
        assertEquals(n1, sorted.get(0));
        assertEquals(n2, sorted.get(1));
        assertEquals(n5, sorted.get(sorted.size() - 1));
    }

    @Test
    public void layer() {
        layout.removeCycles(root);
        layout.layer(root);
        assertEquals(1, n1.getIndex());
        assertEquals(1, n2.getIndex());
        assertEquals(2, n3.getIndex());
        assertEquals(2, n4.getIndex());
        assertEquals(3, n6.getIndex());
        assertEquals(3, n7.getIndex());
        assertEquals(4, n5.getIndex());
    }

    @Test
    public void removeCycles() {
        layout.removeCycles(root);
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
        assertEquals(1, layout.sources(root).size());
    }

    @Test
    public void split() {
        int sn = root.getNodes().size();
        int se = root.getChildEdges().size();
        layout.split(e1_3, root);
        assertEquals(sn + 1, root.getNodes().size());
        assertEquals(se + 1, root.getChildEdges().size());
        Node<?> dn = root.getNode(sn);
        assertEquals(e1_3, dn.getInEdges().get(0));
        Edge<?> de = root.getChildEdge(se);
        assertEquals(de, dn.getOutEdges().get(0));
        assertEquals(dn, de.getNode1());
        assertEquals(n3, de.getNode2());
    }

    @Test
    public void topologicalSort() {
        layout.removeCycles(root);
        List<Node<?>> sorted = layout.topologicalSort(root);
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
        layout.removeCycles(root);
        layout.undoRemoveCycles(root);
        assertFalse(e1_3.isReverted());
        assertFalse(e1_5.isReverted());
        assertFalse(e2_5.isReverted());
        assertFalse(e2_4.isReverted());
        assertFalse(e3_7.isReverted());
        assertFalse(e4_6.isReverted());
        assertFalse(e6_2.isReverted());
        assertFalse(e7_5.isReverted());
    }
    
}
