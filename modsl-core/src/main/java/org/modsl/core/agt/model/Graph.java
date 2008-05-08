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

package org.modsl.core.agt.model;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.modsl.core.agt.common.FontTransform;
import org.modsl.core.agt.visitor.AbstractVisitor;

public class Graph extends AbstractBox {

    /**
     * List of children nodes
     */
    protected List<Node> nodes = new LinkedList<Node>();

    /**
     * List of children labels
     */
    protected List<Label> labels = new LinkedList<Label>();

    /**
     * Map of children nodes {name->node}
     */
    protected Map<String, Node> nodeMap = new HashMap<String, Node>();

    /**
     * List of children edges
     */
    protected List<Edge> edges = new LinkedList<Edge>();

    /**
     * This element's requested size
     */
    protected Pt reqSize = new Pt();

    /**
     * Top padding
     */
    protected double topPadding = 0d;

    /**
     * Bottom padding
     */
    protected double bottomPadding;

    /**
     * Left padding
     */
    protected double leftPadding;

    /**
     * Right padding
     */
    protected double rightPadding;

    public Graph(MetaType type) {
        super(type);
        resetPaddings();
    }

    public Graph(MetaType type, String name) {
        super(type, name);
    }

    /**
     * Add child edge
     * @param child
     */
    public void add(Edge child) {
        child.parent = this;
        edges.add(child);
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
        for (Label l : labels) {
            l.accept(visitor);
        }
        visitor.out(this);
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

    /**
     * Bottom right corner's (x, y)
     * @return max (x, y)
     */
    public Pt getMaxPt() {
        Pt s = new Pt(0d, 0d);
        for (Node n : nodes) {
            s.x = max(s.x, n.pos.x + n.size.x);
            s.y = max(s.y, n.pos.y + n.size.y);
        }
        return s;
    }

    /**
     * Top left corner's (x, y). Can be different from 0, 0 depending on the
     * nodes' positions
     * @return min (x, y)
     */
    public Pt getMinPt() {
        Pt s = new Pt(Double.MAX_VALUE, Double.MAX_VALUE);
        for (Node n : nodes) {
            s.x = min(s.x, n.pos.x);
            s.y = min(s.y, n.pos.y);
        }
        return s;
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

    /**
     * Shift (x, y) on all vertexes to bring min(x, y) to (0, 0)
     */
    public void normalize() {
        Pt min = getMinPt();
        for (Node n : nodes) {
            n.pos.decBy(min);
        }
    }

    /**
     * Recalculates and sets size of this (non-normalized) graph to true size of
     * the non-normalized graph
     */
    public void recalcSize() {
        size = getMaxPt().decBy(getMinPt());
    }

    /**
     * Rescale diagram to the given size
     * @param newSize new size
     */
    public void rescale(Pt newSize) {
        normalize();
        recalcSize();
        Node maxXNode = maxXNode();
        Node maxYNode = maxYNode();
        Pt maxXYSize = new Pt(maxXNode.size.x, maxYNode.size.y);
        Pt newSizeExt = newSize.minus(maxXYSize).decBy(getExtraPadding());
        Pt sizeExt = size.minus(maxXYSize);
        Pt topLeft = new Pt(leftPadding, topPadding);
        for (Node n : nodes) {
            n.pos.mulBy(newSizeExt).divBy(sizeExt).incBy(topLeft);
        }
        size = new Pt(newSize);
    }

    /**
     * Rescale/normalize diagram to it's current content, add paddings
     * @param newSize new size
     */
    public void rescale() {
        recalcSize();
        rescale(size.plus(getExtraPadding()));
    }

    private Pt getExtraPadding() {
        return new Pt(leftPadding + rightPadding + 1, topPadding + bottomPadding + 1);
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

    /**
     * @return node's area in pixels^2
     */
    public double getArea() {
        return size.x * size.y;
    }

    @Override
    public String toString() {
        return name + ":" + type;
    }

}
