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
 * 
 * @author avishnyakov
 * 
 */
public class Edge extends AbstractGraphElement {

    protected Node node1;
    protected Node node2;

    protected String node1Name;
    protected String node2Name;

    public Edge(Node node1, Node node2) {
        this.node1 = node1;
        this.node2 = node2;
    }

    public Edge(String node1Name, String node2Name) {
        this.node1Name = node1Name;
        this.node2Name = node2Name;
    }

    @Override
    public void postCreate() {
        node1 = resolveNode(node1, node1Name);
        node2 = resolveNode(node2, node2Name);
    }

    private Node resolveNode(Node node, String nodeName) {
        if (node == null) {
            Node n = parent.getNode(nodeName);
            if (n == null) {
                throw new InvalidNodeNameException(nodeName);
            }
            return n;
        }
        return node;
    }

    public String toString() {
        return name + "(" + (node1 == null ? "*" + node1Name : node1.getName()) + "-"
                + (node2 == null ? "*" + node2Name : node2.getName()) + ")";
    }

}