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

import static java.lang.Math.max;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.modsl.core.agt.common.ModSLException;
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

    @Override
    public void apply(Node<?> r) {
        this.root = r;
        removeCycles();
        int h = layer();
        insertDummies();
        initLayerIndexes(h);
        reduceCrossings(h);
        undoRemoveCycles();
        layerHeights(h);
        xPositions(h);
        resizeGraph();
        // TODO suppress dummies
    }

    void resizeGraph() {
        root.recalcSize();
        root.normalize();
    }

    void xPositions(int h) {
        for (int l = 1; l <= h; l++) {
            double currOffset = 0d;
            List<Node<?>> ln = getLayerNodes(l);
            for (Node<?> n : ln) {
                n.getPos().x = currOffset;
                currOffset += n.getSize().x + X_SEPARATION;
            }
        }
    }

    void layerHeights(int h) {
        double currOffset = 0d;
        for (int l = 1; l <= h; l++) {
            List<Node<?>> ln = getLayerNodes(l);
            for (Node<?> n : ln) {
                n.getPos().y = currOffset;
            }
            currOffset += maxHeight(ln) + Y_SEPARATION;
        }
    }

    double maxHeight(List<Node<?>> ln) {
        double mh = 0d;
        for (Node<?> n : ln) {
            mh = max(mh, n.getSize().y);
        }
        return mh;
    }

    double barycenter(List<Node<?>> ln) {
        if (ln.size() == 0) {
            return 0;
        } else {
            double bc = 0;
            for (Node<?> n : ln) {
                bc += n.getIndex();
            }
            return bc / ln.size();
        }
    }

    List<Node<?>> getConnectedTo(Node<?> n1, List<Node<?>> sl) {
        List<Node<?>> ln = new LinkedList<Node<?>>();
        for (Node<?> n2 : sl) {
            if (n1.isConnectedTo(n2)) {
                ln.add(n2);
            }
        }
        return ln;
    }

    List<Node<?>> getLayerNodes(int layer) {
        List<Node<?>> ln = new LinkedList<Node<?>>();
        for (Node<?> n : root.getNodes()) {
            if (n.getLayer() == layer) {
                ln.add(n);
            }
        }
        return ln;
    }

    void initLayerIndexes(int h) {
        for (int l = 1; l <= h; l++) {
            setOrderedIndexes(getLayerNodes(l));
        }
    }

    void insertDummies() {
        for (Edge<?> currEdge : new ArrayList<Edge<?>>(root.getChildEdges())) {
            if (currEdge.getNode2().getLayer() - currEdge.getNode1().getLayer() > 1) {
                int fromLayer = currEdge.getNode1().getLayer();
                int toLayer = currEdge.getNode2().getLayer();
                for (int layer = fromLayer + 1; layer < toLayer; layer++) {
                    split(currEdge);
                }
            }
        }
    }

    int layer() {
        List<Node<?>> sorted = topologicalSort();
        for (Node<?> n : sorted) {
            n.setLayer(1);
        }
        int h = 0;
        for (Node<?> n1 : sorted) {
            for (Edge<?> out : n1.getOutEdges()) {
                Node<?> n2 = out.getNode2();
                n2.setLayer(max(n1.getLayer() + 1, n2.getLayer()));
                h = max(h, n2.getLayer());
            }
        }
        return h;
    }

    void reduceCrossings(int h) {
        for (int round = 0; round < 100; round++) {
            if (round % 2 == 0) {
                for (int l = 1; l < h; l++) {
                    reduceCrossings2L(l, l + 1);
                }
            } else {
                for (int l = h; l > 1; l--) {
                    reduceCrossings2L(l, l - 1);
                }
            }
        }
    }

    void reduceCrossings2L(int staticLayer, int layerToShuffle) {
        List<Node<?>> sl = getLayerNodes(staticLayer);
        final List<Node<?>> lts = getLayerNodes(layerToShuffle);
        for (Node<?> n : lts) {
            List<Node<?>> neighbors = getConnectedTo(n, sl);
            double bc = barycenter(neighbors);
            n.setAltIndex(bc);
        }
        Collections.sort(lts, new Comparator<Node<?>>() {
            public int compare(Node<?> n1, Node<?> n2) {
                return (int) ((n1.getAltIndex() - n2.getAltIndex()) * lts.size());
            }
        });
        setOrderedIndexes(lts);
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

    void setOrderedIndexes(List<Node<?>> ln) {
        for (int i = 0; i < ln.size(); i++) {
            ln.get(i).setIndex(i + 1);
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
    Edge<?> split(Edge<?> edge) {
        Node dummyNode = new Node(DUMMY_NODE, "dummyNode" + dummyCount++, true);
        dummyNode.setLayer(edge.getNode1().getLayer() + 1);
        root.add(dummyNode);
        Edge dummyEdge = new Edge(DUMMY_EDGE, "dummyEdge" + dummyCount++, edge.getNode1(), dummyNode, true);
        dummyEdge.setRevertedInternal(edge.isReverted());
        edge.setNode1(dummyNode);
        root.addChild(dummyEdge);
        return edge;
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
