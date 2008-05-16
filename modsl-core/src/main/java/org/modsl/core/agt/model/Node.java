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

    private static int counter = 0;

    /**
     * List of connected edges
     */
    Set<Edge> connectedEdges = new HashSet<Edge>();

    /**
     * Labels
     */
    List<NodeLabel> labels = new LinkedList<NodeLabel>();

    /**
     * Create new
     * @param type type
     * @param name name
     */
    public Node(MetaType type, String name) {
        super(type, name);
        this.index = counter++;
    }

    @Override
    public void accept(AbstractVisitor visitor) {
        visitor.in(this);
        for (NodeLabel l : labels) {
            l.accept(visitor);
        }
        visitor.out(this);
    }

    protected boolean addConnectedEdge(Edge edge) {
        return connectedEdges.add(edge);
    }

    public void addLabel(NodeLabel label) {
        labels.add(label);
        label.setParent(this);
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

    public List<NodeLabel> getLabels() {
        return labels;
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

    public boolean isConnectedTo(AbstractBox<?> b) {
        for (Edge e : connectedEdges) {
            if (e.getDistance(this, b) == 1) {
                return true;
            }
        }
        return false;
    }

    protected boolean removeConnectedEdge(Edge edge) {
        return connectedEdges.remove(edge);
    }

}
