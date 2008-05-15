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
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.modsl.core.agt.common.ModSLException;
import org.modsl.core.agt.layout.AbstractLayoutVisitor;
import org.modsl.core.agt.model.Bend;
import org.modsl.core.agt.model.Edge;
import org.modsl.core.agt.model.Graph;
import org.modsl.core.agt.model.MetaType;
import org.modsl.core.agt.model.Node;

/**
 * Sugiyama layout algorithm
 * @author avishnyakov
 */
public class SugiyamaLayoutVisitor extends AbstractLayoutVisitor {

    protected Graph graph;

    protected SugiyamaLayerStack stack = new SugiyamaLayerStack();

    public SugiyamaLayoutVisitor(MetaType type) {
        super(type);
    }

    @Override
    public void apply(Graph graph) {

        this.graph = graph;
        removeCycles();
        layer();
        insertDummies();
        stack.initIndexes();
        stack.reduceCrossings();
        undoRemoveCycles();
        stack.layerHeights();
        stack.xPositionsAlt();

        //log.debug(new ToStringVisitor().toString(graph));

        graph.rescale();
    }

    @Override
    public String getConfigName() {
        return "sugiyama_layout";
    }

    void insertDummies() {
        for (Edge currEdge : new ArrayList<Edge>(graph.getEdges())) {
            int fromLayer = stack.getLayer(currEdge.getNode1());
            int toLayer = stack.getLayer(currEdge.getNode2());
            if (toLayer - fromLayer > 1) {
                for (int layer = fromLayer + 1; layer < toLayer; layer++) {
                    Bend b = new Bend();
                    currEdge.add(b);
                    stack.add(b, layer);
                }
            }
        }
    }

    void layer() {
        List<Node> sorted = topologicalSort();
        Map<Node, Integer> lmap = new HashMap<Node, Integer>();
        for (Node n : sorted) {
            lmap.put(n, 0);
        }
        int h = 0;
        for (Node n1 : sorted) {
            for (Edge out : n1.getOutEdges()) {
                Node n2 = out.getNode2();
                lmap.put(n2, max(lmap.get(n1) + 1, lmap.get(n2)));
                h = max(h, lmap.get(n2) + 1);
            }
        }
        stack.init(h, sorted.size());
        for (Node n : sorted) {
            stack.add(n, lmap.get(n));
        }
    }

    void removeCycles() {
        List<Node> nodes = sortByOutDegree();
        Set<Edge> removed = new HashSet<Edge>(graph.getEdges().size());
        for (Node n : nodes) {
            for (Edge in : new ArrayList<Edge>(n.getInEdges())) {
                if (!removed.contains(in)) {
                    in.setReverted(true);
                    removed.add(in);
                }
            }
            for (Edge out : n.getOutEdges()) {
                if (!removed.contains(out)) {
                    removed.add(out);
                }
            }
        }
    }

    @Override
    public void setLayoutConfig(Map<String, String> propMap) {
        stack.maxSweeps = Integer.parseInt(propMap.get("maxSweeps"));
        stack.xSeparation = Double.parseDouble(propMap.get("xSeparation"));
        stack.ySeparation = Double.parseDouble(propMap.get("ySeparation"));
    }

    List<Node> sortByOutDegree() {
        List<Node> nodes = new ArrayList<Node>(graph.getNodes());
        Collections.sort(nodes, new Comparator<Node>() {
            public int compare(Node n1, Node n2) {
                return n2.getOutDegree() - n1.getOutDegree();
            }
        });
        return nodes;
    }

    List<Node> sources() {
        List<Node> sources = new LinkedList<Node>();
        for (Node n : graph.getNodes()) {
            if (n.getInDegree() == 0) {
                sources.add(n);
            }
        }
        return sources;
    }

    List<Node> topologicalSort() {
        List<Node> q = sources();
        List<Node> l = new LinkedList<Node>();
        while (q.size() > 0) {
            Node n = q.remove(0);
            l.add(n);
            for (Edge e : n.getOutEdges()) {
                Node m = e.getNode2();
                boolean allEdgesRemoved = true;
                for (Edge e2 : m.getInEdges()) {
                    if (!l.contains(e2.getNode1())) {
                        allEdgesRemoved = false;
                    }
                }
                if (allEdgesRemoved) {
                    q.add(m);
                }
            }
        }
        if (graph.getNodes().size() != l.size()) {
            throw new ModSLException("Topological sort failed for " + graph + " in Sugiyama layout");
        }
        return l;
    }

    void undoRemoveCycles() {
        for (Edge e : graph.getEdges()) {
            e.setReverted(false);
        }
    }

}
