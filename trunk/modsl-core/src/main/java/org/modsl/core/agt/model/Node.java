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

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.modsl.core.agt.visitor.AbstractVisitor;

/**
 * Graph node
 * @author AVishnyakov
 * @param <T> type enum
 */
public class Node extends AbstractBox<Graph> {

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
     * This element's position (it's left top corner) relative to it's parent
     * node
     */
    protected Pt pos = new Pt();

    /**
     * Labels
     */
    protected List<Label> labels = new LinkedList<Label>();

    /**
     * Create new
     * @param type type
     * @param name name
     */
    public Node(MetaType type, String name) {
        super(type, name);
    }

    @Override
    public void accept(AbstractVisitor visitor) {
        visitor.in(this);
        for (Label l : labels) {
            l.accept(visitor);
        }
        visitor.out(this);
    }

    public boolean addConnectedEdge(Edge edge) {
        return connectedEdges.add(edge);
    }

    /**
     * @return alternate position
     */
    public Pt getAltPos() {
        return altPos;
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

    public List<Edge> getInEdges() {
        List<Edge> ins = new LinkedList<Edge>();
        for (Edge e : connectedEdges) {
            if (e.getNode2().equals(this)) {
                ins.add(e);
            }
        }
        return ins;
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

    @Override
    public Pt getSize() {
        return size;
    }

    public boolean isConnectedTo(AbstractBox<?> n2) {
        for (Edge e : connectedEdges) {
            if (e.getDistance(this, n2) == 1) {
                return true;
            }
        }
        return false;
    }

    public boolean removeConnectedEdge(Edge edge) {
        return connectedEdges.remove(edge);
    }

    /**
     * Set alternate position
     * @param altPos
     */
    public void setAltPos(Pt altPos) {
        this.altPos = altPos;
    }

    @Override
    public void setPos(Pt pos) {
        this.pos = pos;
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

}
