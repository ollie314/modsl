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

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.acos;
import static java.lang.Math.signum;

import java.util.LinkedList;
import java.util.List;

import org.modsl.core.agt.visitor.AbstractVisitor;

/**
 * Graph edge
 * @author avishnyakov
 */
public class Edge extends AbstractElement {

    /**
     * Start node
     */
    protected Node node1;

    /**
     * End node
     */
    protected Node node2;

    /**
     * Start node name (will be resolved to node1 reference when post-processing
     */
    protected String node1Name;

    /**
     * End node name (will be resolved to node2 reference when post-processing
     */
    protected String node2Name;

    /**
     * Are start and end nodes swapped? Needed for the layout algorithms.
     */
    protected boolean reverted = false;

    /**
     * Bends
     */
    protected List<Bend> bends = new LinkedList<Bend>();

    /**
     * Labels
     */
    protected List<Label> labels = new LinkedList<Label>();

    /**
     * Create new
     * @param type type
     * @param node1 start node
     * @param node2 end node
     */
    public Edge(MetaType type, Node node1, Node node2) {
        super(type);
        this.node1 = node1;
        this.node1.addConnectedEdge(this);
        this.node2 = node2;
        this.node2.addConnectedEdge(this);
    }

    /**
     * Create new
     * @param type type
     * @param name name
     * @param node1 start node
     * @param node2 end node
     */
    public Edge(MetaType type, String name, Node node1, Node node2) {
        this(type, node1, node2);
        this.name = name;
    }

    @Override
    public void accept(AbstractVisitor visitor) {
        visitor.in(this);
        for (Bend b : bends) {
            b.accept(visitor);
        }
        for (Label l : labels) {
            l.accept(visitor);
        }
        visitor.out(this);
    }

    /**
     * Add new bend
     * @param bend
     */
    public void add(Bend bend) {
        bends.add(bend);
        bend.setParent(this);
    }

    /**
     * @return angle at node 2
     */
    public double angle2() {
        Pt delta = getDelta2();
        if (delta.y > 0d) {
            return acos(this.cos2());
        } else if (delta.y < 0d) {
            return 2 * PI - acos(this.cos2());
        } else {
            if (delta.x >= 0d) {
                return 0;
            } else {
                return PI;
            }
        }
    }

    /**
     * @return cos at node 1
     */
    public double cos1() {
        Pt delta = getDelta1();
        return delta.x / delta.len();
    }

    /**
     * @return cos at node 2
     */
    public double cos2() {
        Pt delta = getDelta2();
        return delta.x / delta.len();
    }

    /**
     * @param index
     * @return bend
     */
    public Bend getBend(int index) {
        return bends.get(index);
    }

    /**
     * @return all bends
     */
    public List<Bend> getBends() {
        return bends;
    }

    /**
     * @return (delta(x), delta(y)) between node1's center pos and the first
     * bend
     */
    public Pt getDelta1() {
        return getFirstBend().getCtrPos().minus(node1.getCtrPos());
    }

    /**
     * @return (delta(x), delta(y)) between last bend and the node2's center pos
     */
    public Pt getDelta2() {
        return node2.getCtrPos().minus(getLastBend().getCtrPos());
    }

    /**
     * @param p1
     * @param p2
     * @return distance (in hops) between nodes or bends this edge connects
     */
    public int getDistance(AbstractBox p1, AbstractBox p2) {
        int p1i = -2, p2i = -2;
        p1i = p1.equals(node1) ? -1 : p1i;
        p2i = p2.equals(node1) ? -1 : p2i;
        int t = bends.indexOf(p1);
        p1i = t > -1 ? t : p1i;
        t = bends.indexOf(p2);
        p2i = t > -1 ? t : p2i;
        p1i = p1.equals(node2) ? bends.size() : p1i;
        p2i = p2.equals(node2) ? bends.size() : p2i;
        return p1i > -2 && p2i > -2 ? abs(p2i - p1i) : Integer.MAX_VALUE;
    }

    /**
     * @return get first bend after the node 1 (will return node 2 if there are
     * no bends)
     */
    public AbstractBox getFirstBend() {
        if (bends.isEmpty()) {
            return node2;
        } else {
            return bends.get(0);
        }
    }

    /**
     * @return get last bend before the node 2 (will return node 1 if there are
     * no bends)
     */
    public AbstractBox getLastBend() {
        if (bends.isEmpty()) {
            return node1;
        } else {
            return bends.get(bends.size() - 1);
        }
    }

    /**
     * @return length of this edge (staright line between center positions) w/o
     * adjustment for overlay
     */
    public double getLength() {
        return node2.getCtrPos().minus(node1.getCtrPos()).len();
    }

    /**
     * @return start node
     */
    public Node getNode1() {
        return node1;
    }

    /**
     * @return start node name
     */
    public String getNode1Name() {
        return node1Name;
    }

    /**
     * @return start point position at node 1
     */
    public Pt getNode1Port() {
        return getNodePort(node1, 1d, sin1(), cos1(), tan1());
    }

    /**
     * @return end node
     */
    public Node getNode2() {
        return node2;
    }

    /**
     * @return end node name
     */
    public String getNode2Name() {
        return node2Name;
    }

    /**
     * @return end point position at node 2
     */
    public Pt getNode2Port() {
        return getNodePort(node2, -1d, sin2(), cos2(), tan2());
    }

    /**
     * Convenience method to calculate adjusted position of the connector's
     * endpoint at the given node, considering that element's dimensions
     * @return position adjusted to node's size
     */
    protected Pt getNodePort(Node n, double sign, double sin, double cos, double tan) {
        Pt ap = new Pt();
        Pt cp = n.getCtrPos();
        Pt s = n.getSize();
        if (abs(n.tan()) > abs(tan)) {
            // i.e. line is crossing e2's side
            ap.x = cp.x + sign * s.x * signum(cos) / 2d;
            ap.y = cp.y + sign * s.x * tan * signum(cos) / 2d;
        } else {
            ap.x = cp.x + sign * s.y / tan * signum(sin) / 2d;
            ap.y = cp.y + sign * s.y * signum(sin) / 2d;
        }
        return ap;
    }

    /**
     * @return true is reverted (by layout algorithm)
     */
    public boolean isReverted() {
        return reverted;
    }

    /**
     * Set start node
     * @param n1 start node
     */
    public void setNode1(Node n1) {
        if (node1 != null && !node1.equals(n1)) {
            node1.removeConnectedEdge(this);
            node1 = n1;
            node1.addConnectedEdge(this);
        }
    }

    /**
     * Set end node
     * @param n2 end node
     */
    public void setNode2(Node n2) {
        if (node2 != null && !node2.equals(n2)) {
            node2.removeConnectedEdge(this);
            node2 = n2;
            node2.addConnectedEdge(this);
        }
    }

    /**
     * Revert edge's direction (layout)
     * @param r
     */
    public void setReverted(boolean r) {
        if (reverted != r) {
            Node tn = node1;
            node1 = node2;
            node2 = tn;
            String tnn = node1Name;
            node1Name = node2Name;
            node2Name = tnn;
            reverted = r;
        }
    }

    /**
     * @return sin at node 1
     */
    public double sin1() {
        Pt delta = getDelta1();
        return delta.y / delta.len();
    }

    /**
     * @return sin at node 2
     */
    public double sin2() {
        Pt delta = getDelta2();
        return delta.y / delta.len();
    }

    /**
     * @return tan at node 1
     */
    public double tan1() {
        Pt delta = getDelta1();
        return delta.y / delta.x;
    }

    /**
     * @return tan at node 2
     */
    public double tan2() {
        Pt delta = getDelta2();
        return delta.y / delta.x;
    }

    @Override
    public String toString() {
        return name + ":" + type + "(" + (node1 == null ? "*" + node1Name : node1.getName()) + "->"
                + (node2 == null ? "*" + node2Name : node2.getName()) + ")";
    }

}