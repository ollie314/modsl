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
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.modsl.core.agt.common.FontTransform;
import org.modsl.core.agt.visitor.AbstractVisitor;

/**
 * Graph node
 * @author AVishnyakov
 * @param <T> type enum
 */
public class Node extends AbstractGraphElement implements Point {

    /**
     * List of children nodes
     */
    protected List<Node> nodes = new LinkedList<Node>();

    /**
     * Map of children nodes {name->node}
     */
    protected Map<String, Node> nodeMap = new HashMap<String, Node>();

    /**
     * List of children edges
     */
    protected List<Edge> childEdges = new LinkedList<Edge>();

    /**
     * List of connected edges
     */
    protected Set<Edge> connectedEdges = new HashSet<Edge>();

    /**
     * This element's size
     */
    protected Pt size = new Pt();

    /**
     * Alternate position (place holder for layout algorithms)
     */
    protected Pt altPos = new Pt();

    /**
     * This element's requested size
     */
    protected Pt reqSize = new Pt();

    /**
     * This element's position (it's left top corner) relative to it's parent
     * node
     */
    protected Pt pos = new Pt();

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

    /**
     * Temp index holder (layout algorithms)
     */
    protected int index = -1;

    /**
     * Create new
     * @param type type
     */
    public Node(MetaType type) {
        super(type);
        resetPaddings();
    }

    /**
     * Create new
     * @param type type
     * @param name name
     */
    public Node(MetaType type, String name) {
        super(type, name);
        resetPaddings();
    }

    @Override
    public void accept(AbstractVisitor visitor) {
        visitor.in(this);
        for (Edge e : childEdges) {
            e.accept(visitor);
        }
        for (Node n : nodes) {
            n.accept(visitor);
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
     * Add child edge
     * @param child
     */
    public void addChild(Edge child) {
        child.parent = this;
        childEdges.add(child);
    }

    public boolean addConnectedEdge(Edge edge) {
        return connectedEdges.add(edge);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Node) {
            if (this == obj) {
                return true;
            } else {
                return name.equals(((Node) obj).name);
            }
        } else {
            return false;
        }
    }

    /**
     * @return alternate position
     */
    public Pt getAltPos() {
        return altPos;
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
    public Edge getChildEdge(int index) {
        return childEdges.get(index);
    }

    /**
     * @return children edge list
     */
    public List<Edge> getChildEdges() {
        return childEdges;
    }

    public Edge getConnnectedEdgeTo(Node n2) {
        for (Edge e : connectedEdges) {
            if (n2.equals(e.getNode1()) || n2.equals(e.getNode2())) {
                return e;
            }
        }
        return null;
    }

    @Override
    public Pt getCtrPos() {
        return pos.plus(size.div(2d));
    }

    public int getInDegree() {
        return getInEdges().size();
    }

    public int getIndex() {
        return index;
    }

    public List<Edge> getInEdges() {
        List<Edge> ins = new LinkedList<Edge>();
        for (Edge e : connectedEdges) {
            if (e.getNode2().equals(this)) {
                ins.add(e);
            }
        }
        return ins;
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

    public int getOutDegree() {
        return getOutEdges().size();
    }

    public List<Edge> getOutEdges() {
        List<Edge> ins = new LinkedList<Edge>();
        for (Edge e : connectedEdges) {
            if (e.getNode1().equals(this)) {
                ins.add(e);
            }
        }
        return ins;
    }

    @Override
    public Pt getPos() {
        return pos;
    }

    /**
     * @return size requested by the client
     */
    public Pt getReqSize() {
        return reqSize;
    }

    @Override
    public Pt getSize() {
        return size;
    }

    /**
     * @return sum of all edge lengths
     */
    public double getSumChildEdgeLengths() {
        double len = 0d;
        for (Edge e : childEdges) {
            len += e.getLength();
        }
        return len;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean isConnectedTo(Point n2) {
        for (Edge e : connectedEdges) {
            if (e.getDistance(this, n2) == 1) {
                return true;
            }
        }
        return false;
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

    public boolean removeConnectedEdge(Edge edge) {
        return connectedEdges.remove(edge);
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
     * Set alternate position
     * @param altPos
     */
    public void setAltPos(Pt altPos) {
        this.altPos = altPos;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public void setPos(Pt pos) {
        this.pos = pos;
    }

    /**
     * @param reqSize size requested by client (if any)
     */
    public void setReqSize(Pt reqSize) {
        this.reqSize = reqSize;
    }

    @Override
    public void setSize(Pt size) {
        this.size = size;
    }

    /**
     * @return sin of angle between 0 and diagonal
     */
    public double sin() {
        return size.y / size.len();
    }

    /**
     * @return tan of angle between 0 and diagonal
     */
    public double tan() {
        return size.y / size.x;
    }

    @Override
    public String toString() {
        return name + "(" + index + "):" + type;
    }

    @Override
    public boolean isVirtual() {
        return false;
    }

}
