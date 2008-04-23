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

package org.modsl.agt;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Graph node
 * 
 * @author avishnyakov
 */
public class Node extends AbstractGraphElement {

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
    protected List<Edge> edges = new LinkedList<Edge>();

    /**
     * Map of children edges {name->edge}
     */
    protected Map<String, Edge> edgeMap = new HashMap<String, Edge>();

    public Node() {
        super();
    }

    public Node(String name) {
        super(name);
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

    public Node getNode(String key) {
        return nodeMap.get(key);
    }

    /**
     * @return children node list
     */
    public List<Node> getNodes() {
        return nodes;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(name);
        if (nodes.size() > 0) {
            sb.append(" {");
            boolean first = true;
            for (Node n : nodes) {
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
            for (Edge e : edges) {
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
    public void add(Edge child) {
        child.parent = this;
        edges.add(child);
        edgeMap.put(child.getName(), child);
    }

    public Edge getEdge(String key) {
        return edgeMap.get(key);
    }

    /**
     * @return children edge list
     */
    public List<Edge> getEdges() {
        return edges;
    }

    @Override
    public void postCreate() {
        for (Node n: nodes) {
            n.postCreate();
        }
        log.debug("Postcreate " + getName());
    }

}
