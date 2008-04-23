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
    protected List<Node> children;

    /**
     * Map of children nodes {name->node}
     */
    protected Map<String, Node> childrenMap;

    public Node() {
        super();
    }

    /**
     * Create new node
     * @param parent parent node
     * @param name node name
     */
    public Node(Node parent, String name) {
        super();
        this.parent = parent;
        this.name = name;
    }

    /**
     * Add child node
     * @param child
     */
    public void add(Node child) {
        if (children == null) {
            children = new LinkedList<Node>();
        }
        if (childrenMap == null) {
            childrenMap = new HashMap<String, Node>();
        }
        children.add(child);
        childrenMap.put(child.getName(), child);
    }

    /**
     * @return children node list
     */
    public List<Node> getChildren() {
        return children;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        if (parent == null) {
            sb.append("(");
        }
        sb.append(name);
        if (children != null) {
            sb.append(" (");
            boolean first = true;
            for (Node n : children) {
                if (first) {
                    first = false;
                } else {
                    sb.append(" ");
                }
                sb.append(n.toString());
            }
            sb.append(")");
        }
        if (parent == null) {
            sb.append(")");
        }
        return sb.toString();
    }

}
