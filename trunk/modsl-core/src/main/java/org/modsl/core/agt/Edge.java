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

package org.modsl.core.agt;

/**
 * Graph edge
 * @param T type enum
 * @author avishnyakov
 */
public class Edge<T extends AGTType> extends AbstractGraphElement<T> {

    /**
     * Start node
     */
    protected Node<T> node1;

    /**
     * End nodde
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

    @Override
    public String toString() {
        return name + ":" + type + "(" + (node1 == null ? "*" + node1Name : node1.getName()) + "-"
                + (node2 == null ? "*" + node2Name : node2.getName()) + ")";
    }

    @Override
    public void accept(AGTVisitor<T> visitor) {
        visitor.visit(this);
    }

    public Node<T> getNode1() {
        return node1;
    }

    public void setNode1(Node<T> node1) {
        this.node1 = node1;
    }

    public Node<T> getNode2() {
        return node2;
    }

    public void setNode2(Node<T> node2) {
        this.node2 = node2;
    }

    public String getNode1Name() {
        return node1Name;
    }

    public String getNode2Name() {
        return node2Name;
    }

}