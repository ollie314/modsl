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

package org.modsl.core.agt.layout.sugiyama;

import static java.lang.Math.max;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.modsl.core.agt.common.ModSLException;
import org.modsl.core.agt.layout.AbstractNonConfigurableLayout;
import org.modsl.core.agt.model.Edge;
import org.modsl.core.agt.model.MetaType;
import org.modsl.core.agt.model.Node;
import org.modsl.core.lang.uml.UMLMetaType;

public class SugiyamaLayout extends AbstractNonConfigurableLayout {

    protected static final double X_SEPARATION = 20d;

    protected static final double Y_SEPARATION = 50d;
    protected static MetaType DUMMY_EDGE = UMLMetaType.DUMMY_EDGE;

    protected static MetaType DUMMY_NODE = UMLMetaType.DUMMY_NODE;
    protected int dummyCount = 1;

    protected Node<?> root;
    protected SugiyamaLayerStack stack;

    @Override
    public void apply(Node<?> r) {
        this.root = r;
        removeCycles();
        layer();
        insertDummies();
        stack.initIndexes();
        stack.reduceCrossings();
        undoRemoveCycles();
        stack.layerHeights();
        stack.xPositions();
        root.rescale();
    }

    void insertDummies() {
        for (Edge<?> currEdge : new ArrayList<Edge<?>>(root.getChildEdges())) {
            int fromLayer = stack.getLayer(currEdge.getNode1());
            int toLayer = stack.getLayer(currEdge.getNode2());
            if (toLayer - fromLayer > 1) {
                for (int layer = fromLayer + 1; layer < toLayer; layer++) {
                    split(currEdge);
                }
            }
        }
    }

    void layer() {
        List<Node<?>> sorted = topologicalSort();
        for (Node<?> n : sorted) {
            n.setIndex(0);
        }
        int h = 0;
        for (Node<?> n1 : sorted) {
            for (Edge<?> out : n1.getOutEdges()) {
                Node<?> n2 = out.getNode2();
                n2.setIndex(max(n1.getIndex() + 1, n2.getIndex()));
                h = max(h, n2.getIndex() + 1);
            }
        }
        stack = new SugiyamaLayerStack(h, sorted.size());
        for (Node<?> n1 : sorted) {
            stack.add(n1, n1.getIndex());
        }
    }

    void removeCycles() {
        List<Node<?>> nodes = sortByOutDegree();
        Set<Edge<?>> removed = new HashSet<Edge<?>>(root.getChildEdges().size());
        for (Node<?> n : nodes) {
            for (Edge<?> in : new ArrayList<Edge<?>>(n.getInEdges())) {
                if (!removed.contains(in)) {
                    in.setReverted(true);
                    removed.add(in);
                }
            }
            for (Edge<?> out : n.getOutEdges()) {
                if (!removed.contains(out)) {
                    removed.add(out);
                }
            }
        }
    }

    List<Node<?>> sortByOutDegree() {
        List<Node<?>> nodes = new ArrayList<Node<?>>(root.getNodes());
        Collections.sort(nodes, new Comparator<Node<?>>() {
            public int compare(Node<?> n1, Node<?> n2) {
                return n2.getOutDegree() - n1.getOutDegree();
            }
        });
        return nodes;
    }

    List<Node<?>> sources() {
        List<Node<?>> sources = new LinkedList<Node<?>>();
        for (Node<?> n : root.getNodes()) {
            if (n.getInDegree() == 0) {
                sources.add(n);
            }
        }
        return sources;
    }

    @SuppressWarnings("unchecked")
    void split(Edge<?> edge) {
        Node dummyNode = new Node(DUMMY_NODE, "dummyNode" + dummyCount++, true);
        stack.add(dummyNode, stack.getLayer(edge.getNode1()) + 1);
        root.add(dummyNode);
        Edge dummyEdge = new Edge(DUMMY_EDGE, "dummyEdge" + dummyCount++, edge.getNode1(), dummyNode, true);
        dummyEdge.setRevertedInternal(edge.isReverted());
        edge.setNode1(dummyNode);
        root.addChild(dummyEdge);
    }

    List<Node<?>> topologicalSort() {
        List<Node<?>> q = sources();
        List<Node<?>> l = new LinkedList<Node<?>>();
        while (q.size() > 0) {
            Node<?> n = q.remove(0);
            l.add(n);
            for (Edge<?> e : n.getOutEdges()) {
                Node<?> m = e.getNode2();
                boolean allEdgesRemoved = true;
                for (Edge<?> e2 : m.getInEdges()) {
                    if (!l.contains(e2.getNode1())) {
                        allEdgesRemoved = false;
                    }
                }
                if (allEdgesRemoved) {
                    q.add(m);
                }
            }
        }
        if (root.getNodes().size() != l.size()) {
            throw new ModSLException("Topological sort failed for " + root + " in Sugiyama layout");
        }
        return l;
    }

    void undoRemoveCycles() {
        for (Edge<?> e : root.getChildEdges()) {
            e.setReverted(false);
        }
    }

}
