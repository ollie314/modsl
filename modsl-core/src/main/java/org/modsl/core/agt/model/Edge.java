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

import org.modsl.core.agt.visitor.AbstractVisitor;

/**
 * Graph edge. 
 * 
 * @param T type enum
 * @author avishnyakov
 */
public class Edge<T extends MetaType> extends AbstractGraphElement<T> {

    /**
     * Start node
     */
    protected Node<T> node1;

    /**
     * End node
     */
    protected Node<T> node2;

    /**
     * Start node name (will be resolved to node1 reference when post-processing
     */
    protected String node1Name;

    /**
     * End node name (will be resolved to node2 reference when post-processing
     */
    protected String node2Name;

    /**
     * Create new
     * @param type type
     * @param node1 start node
     * @param node2 end node
     */
    public Edge(T type, Node<T> node1, Node<T> node2) {
        super(type);
        this.node1 = node1;
        this.node2 = node2;
    }

    /**
     * Create new
     * @param type type
     * @param name name
     * @param node1 start node
     * @param node2 end node
     */
    public Edge(T type, String name, Node<T> node1, Node<T> node2) {
        this(type, node1, node2);
        this.name = name;
    }

    @Override
    public void accept(AbstractVisitor<T> visitor) {
        visitor.in(this);
        visitor.out(this);
    }

    /**
     * @return length of this edge w/o adjustment for overlay
     */
	public double getLength() {
		return node2.getPos().minus(node1.getPos()).len();
	}

    /**
     * @return start node
     */
    public Node<T> getNode1() {
        return node1;
    }

    /**
     * @return start node name
     */
    public String getNode1Name() {
        return node1Name;
    }

    /**
     * @return end node
     */
    public Node<T> getNode2() {
        return node2;
    }

    /**
     * @return end node name
     */
    public String getNode2Name() {
        return node2Name;
    }

    /**
     * Set start node
     * @param node1 
     */
    public void setNode1(Node<T> node1) {
        this.node1 = node1;
    }

    /**
     * Set end node
     * @param node2
     */
    public void setNode2(Node<T> node2) {
        this.node2 = node2;
    }

    @Override
    public String toString() {
        return name + ":" + type + "(" + (node1 == null ? "*" + node1Name : node1.getName()) + "-"
                + (node2 == null ? "*" + node2Name : node2.getName()) + ")";
    }

}