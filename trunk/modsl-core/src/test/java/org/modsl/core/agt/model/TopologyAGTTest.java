package org.modsl.core.agt.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TopologyAGTTest extends AbstractAGTModelTest {

    @Test
    public void ins() {
        assertEquals(0, root.getNode("TopLeft").getInDegree());
        assertEquals(0, root.getNode("TopRight").getInDegree());
        assertEquals(1, root.getNode("BottomRight").getInDegree());
        assertEquals(3, root.getNode("BottomMiddle").getInDegree());
    }

    @Test
    public void outs() {
        assertEquals(2, root.getNode("TopLeft").getOutDegree());
        assertEquals(2, root.getNode("TopRight").getOutDegree());
        assertEquals(1, root.getNode("BottomRight").getOutDegree());
        assertEquals(0, root.getNode("BottomMiddle").getOutDegree());
    }
}
