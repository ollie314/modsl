package org.modsl.core.agt.layout.fr2;

import static org.junit.Assert.assertEquals;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.modsl.core.agt.TMetaType;
import org.modsl.core.agt.model.Edge;
import org.modsl.core.agt.model.Graph;
import org.modsl.core.agt.model.Node;
import org.modsl.core.agt.model.Pt;
import org.modsl.core.agt.visitor.ToStringVisitor;

public class FR2LayoutVisitorTest {

    Logger log = Logger.getLogger(getClass());

    Graph graph;
    Node n1;
    Node n2;
    Edge e1_2;

    FR2LayoutVisitor layout = new FR2LayoutVisitor(TMetaType.GRAPH);

    @Before
    public void setUp() throws Exception {

        graph = new Graph(TMetaType.GRAPH, "g");
        n1 = new Node(TMetaType.NODE, "n1");
        n2 = new Node(TMetaType.NODE, "n2");
        e1_2 = new Edge(TMetaType.EDGE, n1, n2);

        graph.setReqSize(new Pt(100d, 100d));
        graph.add(n1);
        n1.setSize(new Pt(10d, 10d));
        graph.add(n2);
        n2.setSize(new Pt(10d, 10d));
        graph.add(e1_2);

        layout.maxIterations = 100;
        layout.tempMultiplier = 0.05d;
        layout.attractionMultiplier = 0.75d;
        layout.repulsionMultiplier = 0.75d;

    }

    @Test
    public void twoBit() {
        layout.in(graph);
        assertEquals(60d, n1.getCtrDelta(n2).len(), 10d);
        // log.debug(new ToStringVisitor().toString(graph));
    }

    @Test
    public void barsv() {
        layout.bars.add(new Bar(true, 0, 0d));
        n1.setIndex(0);
        layout.bars.add(new Bar(true, 100, 0d));
        n2.setIndex(1);
        layout.in(graph);
        // log.debug(new ToStringVisitor().toString(graph));
    }

    @Test
    public void barsh() {
        layout.bars.add(new Bar(false, 0, 0d));
        n1.setIndex(0);
        layout.bars.add(new Bar(false, 100, 0d));
        n2.setIndex(1);
        layout.in(graph);
        // log.debug(new ToStringVisitor().toString(graph));
    }

    @Test
    public void weight() {
        double w = 1d;
        for (Node n : graph.getNodes()) {
            n.setWeight(w++);
        }
        layout.in(graph);
        log.debug(new ToStringVisitor().toString(graph));
    }

}
