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

package org.modsl.core.agt.model;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.modsl.core.agt.common.FontTransform;
import org.modsl.core.agt.visitor.AbstractVisitor;

public class Graph extends AbstractBox<Graph> {

    private static int counter = 0;

    /**
     * List of children nodes
     */
    List<Node> nodes = new LinkedList<Node>();

    /**
     * Labels
     */
    List<Label> labels = new LinkedList<Label>();

    /**
     * Map of children nodes {name->node}
     */
    Map<String, Node> nodeMap = new HashMap<String, Node>();

    /**
     * List of children edges
     */
    List<Edge> edges = new LinkedList<Edge>();

    /**
     * This element's requested size
     */
    Pt reqSize = new Pt();

    /**
     * Top padding
     */
    double topPadding = 0d;

    /**
     * Bottom padding
     */
    double bottomPadding;

    /**
     * Left padding
     */
    double leftPadding;

    /**
     * Right padding
     */
    double rightPadding;

    public Graph(MetaType type) {
        super(type);
        this.index = counter++;
        resetPaddings();
    }

    public Graph(MetaType type, String name) {
        super(type, name);
        resetPaddings();
        this.index = counter++;
    }

    @Override
    public void accept(AbstractVisitor visitor) {
        visitor.in(this);
        for (Edge e : edges) {
            e.accept(visitor);
        }
        for (Node n : nodes) {
            n.accept(visitor);
        }
        for (Label l : getLabels()) {
            l.accept(visitor);
        }
        visitor.out(this);
    }

    /**
     * Add child edge
     * @param child
     */
    public void add(Edge child) {
        child.parent = this;
        edges.add(child);
    }

    /**
     * Add child node
     * @param child
     */
    public void add(Node child) {
        child.parent = this;
        nodes.add(child);
        nodeMap.put(child.getName(), child);
    }

    /**
     * @return node's area in pixels^2
     */
    public double getArea() {
        return size.x * size.y;
    }

    /**
     * @param index
     * @return edge by index
     */
    public Edge getEdge(int index) {
        return edges.get(index);
    }

    /**
     * @return children edge list
     */
    public List<Edge> getEdges() {
        return edges;
    }

    private Pt getExtraPadding() {
        return new Pt(leftPadding + rightPadding + 1, topPadding + bottomPadding + 1);
    }

    public List<Label> getLabels() {
        return labels;
    }

    /**
     * @param index
     * @return node by index
     */
    public Node getNode(int index) {
        return nodes.get(index);
    }

    /**
     * @param key
     * @return node by it's name
     */
    public Node getNode(String key) {
        return nodeMap.get(key);
    }

    /**
     * @return children node list
     */
    public List<Node> getNodes() {
        return nodes;
    }

    /**
     * @return size requested by the client
     */
    public Pt getReqSize() {
        return reqSize;
    }

    /**
     * @return sum of all edge lengths
     */
    public double getSumChildEdgeLengths() {
        double len = 0d;
        for (Edge e : edges) {
            len += e.getLength();
        }
        return len;
    }

    /**
     * @return max position
     */
    public Pt maxPos() {
        Pt s = new Pt(-Double.MAX_VALUE, -Double.MAX_VALUE);
        for (Node n : nodes) {
            s.x = max(s.x, n.pos.x);
            s.y = max(s.y, n.pos.y);
        }
        return s;
    }

    /**
     * Bottom right corner's (x, y)
     * @return max (x, y)
     */
    public Pt maxPt() {
        Pt s = new Pt(-Double.MAX_VALUE, -Double.MAX_VALUE);
        for (Node n : nodes) {
            s.x = max(s.x, n.pos.x + n.size.x);
            s.y = max(s.y, n.pos.y + n.size.y);
        }
        for (Edge e : edges) {
            Pt p = e.getMaxPt();
            s.x = max(s.x, p.x);
            s.y = max(s.y, p.y);
        }
        for (Label l : getLabels()) {
            s.x = max(s.x, l.pos.x + l.size.x);
            s.y = max(s.y, l.pos.y + l.size.y);
        }
        return s;
    }

    /**
     * @return node with max x (the rightmost one, including it's size)
     */
    public Node maxXNode() {
        Node res = null;
        for (Node n : nodes) {
            res = res == null ? n : (res.pos.x + res.size.x < n.pos.x + n.size.x ? n : res);
        }
        return res;
    }

    /**
     * @return node with max y (the lowest one, including it's size)
     */
    public Node maxYNode() {
        Node res = null;
        for (Node n : nodes) {
            res = res == null ? n : (res.pos.y + res.size.y < n.pos.y + n.size.y ? n : res);
        }
        return res;
    }

    public Pt minPos() {
        return minPt();
    }

    /**
     * Top left corner's (x, y). Can be different from 0, 0 depending on the
     * nodes' positions
     * @return min (x, y)
     */
    public Pt minPt() {
        Pt s = new Pt(Double.MAX_VALUE, Double.MAX_VALUE);
        for (Node n : nodes) {
            s.x = min(s.x, n.pos.x);
            s.y = min(s.y, n.pos.y);
        }
        for (Edge e : edges) {
            Pt p = e.getMinPt();
            s.x = min(s.x, p.x);
            s.y = min(s.y, p.y);
        }
        for (Label l : getLabels()) {
            s.x = min(s.x, l.pos.x);
            s.y = min(s.y, l.pos.y);
        }
        return s;
    }

    /**
     * Shift (x, y) on all vertexes to bring min(x, y) to (0, 0)
     */
    public void normalize() {
        Pt min = minPt();
        for (Label l : getLabels()) {
            l.pos.decBy(min);
        }
        for (Node n : nodes) {
            n.pos.decBy(min);
            for (Label l : n.getLabels()) {
                l.pos.decBy(min);
            }
        }
        for (Edge e : edges) {
            for (Label l : e.getLabels()) {
                l.pos.decBy(min);
            }
            for (Bend b : e.getBends()) {
                b.pos.decBy(min);
            }
        }
    }

    public void randomize(long seed) {
        Random r = new Random(seed);
        for (Node n : nodes) {
            n.setPos(new Pt(r.nextDouble(), r.nextDouble()));
        }
    }

    /**
     * Recalculates and sets size of this (non-normalized) graph to true size of
     * the non-normalized graph
     */
    public void recalcSize() {
        size = maxPt().decBy(minPt());
    }

    /**
     * Rescale/normalize diagram to it's current content, add paddings
     * @param newSize new size
     */
    public void rescale() {
        recalcSize();
        rescale(size.plus(getExtraPadding()));
    }

    /**
     * Rescale diagram to the given size
     * @param newSize new size
     */
    public void rescale(Pt newSize) {
        if (nodes.isEmpty()) {
            return;
        }
        normalize();
        recalcSize();
        Node maxXNode = maxXNode();
        Node maxYNode = maxYNode();
        Pt maxXYSize = new Pt(maxXNode.size.x, maxYNode.size.y);
        Pt newSizeExt = newSize.minus(maxXYSize).decBy(getExtraPadding());
        Pt sizeExt = size.minus(maxXYSize);
        Pt topLeft = new Pt(leftPadding, topPadding);
        for (Label l : getLabels()) {
            l.pos.mulBy(newSizeExt).divBy(sizeExt).incBy(topLeft);
        }
        for (Node n : nodes) {
            n.pos.mulBy(newSizeExt).divBy(sizeExt).incBy(topLeft);
            for (Label l : n.getLabels()) {
                l.pos.mulBy(newSizeExt).divBy(sizeExt).incBy(topLeft);
            }
        }
        for (Edge e : edges) {
            for (Label l : e.getLabels()) {
                l.pos.mulBy(newSizeExt).divBy(sizeExt).incBy(topLeft);
            }
            for (Bend b : e.getBends()) {
                b.pos.mulBy(newSizeExt).divBy(sizeExt).incBy(topLeft);
            }
        }
        size = new Pt(newSize);
    }

    /**
     * Resets paddings to the values dictated by font transform object
     * (essentially based on the font size)
     */
    private void resetPaddings() {
        FontTransform ft = type.getConfig().getFontTransform();
        if (ft != null) {
            leftPadding = ft.getLeftPadding();
            rightPadding = ft.getRightPadding();
            topPadding = ft.getTopPadding();
            bottomPadding = ft.getBottomPadding();
        }
    }

    /**
     * @param reqSize size requested by client (if any)
     */
    public void setReqSize(Pt reqSize) {
        this.reqSize = reqSize;
    }

}
