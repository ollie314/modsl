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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Graph node
 * 
 * @author avishnyakov
 */
public class Node<T extends AGTType> extends AbstractGraphElement<T> {

    /**
     * List of children nodes
     */
    protected List<Node<T>> nodes = new LinkedList<Node<T>>();

    /**
     * Map of children nodes {name->node}
     */
    protected Map<String, Node<T>> nodeMap = new HashMap<String, Node<T>>();

    /**
     * List of children edges
     */
    protected List<Edge<T>> edges = new LinkedList<Edge<T>>();

    /**
     * Map of children edges {name->edge}
     */
    protected Map<String, Edge<T>> edgeMap = new HashMap<String, Edge<T>>();

    public Node(T type, String name) {
        super(type, name);
    }

    public Node(T type) {
        super(type);
    }

    /**
     * Add child node
     * @param child
     */
    public void add(Node<T> child) {
        child.parent = this;
        nodes.add(child);
        nodeMap.put(child.getName(), child);
    }

    public Node<T> getNode(String key) {
        return nodeMap.get(key);
    }

    /**
     * @return children node list
     */
    public List<Node<T>> getNodes() {
        return nodes;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(name);
        sb.append(":").append(type);
        if (nodes.size() > 0) {
            sb.append(" {");
            boolean first = true;
            for (Node<T> n : nodes) {
                if (first) {
                    first = false;
                } else {
                    sb.append(" ");
                }
                sb.append(n.toString());
            }
            sb.append("}");
        }
        if (edges.size() > 0) {
            sb.append(" [");
            boolean first = true;
            for (Edge<T> e : edges) {
                if (first) {
                    first = false;
                } else {
                    sb.append(" ");
                }
                sb.append(e.toString());
            }
            sb.append("]");
        }
        return sb.toString();
    }

    /**
     * Add child edge
     * @param child
     */
    public void add(Edge<T> child) {
        child.parent = this;
        edges.add(child);
        edgeMap.put(child.getName(), child);
    }

    public Edge<T> getEdge(String key) {
        return edgeMap.get(key);
    }

    /**
     * @return children edge list
     */
    public List<Edge<T>> getEdges() {
        return edges;
    }

    @Override
    public void postCreate() {
        for (Node<T> n : nodes) {
            n.postCreate();
        }
        for (Edge<T> e : edges) {
            e.postCreate();
        }
    }

}
