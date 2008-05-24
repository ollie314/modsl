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

package org.modsl.core.lang.uml.factory;

import java.util.LinkedList;

import org.modsl.core.agt.model.Edge;
import org.modsl.core.agt.model.EdgeLabel;
import org.modsl.core.agt.model.Graph;
import org.modsl.core.agt.model.Node;
import org.modsl.core.agt.model.NodeLabel;
import org.modsl.core.lang.uml.UMLMetaType;

/**
 * Responsible for supporting the grammar when new abstract graph tree elements
 * need to be instantiated.
 * @author AVishnyakov
 */
public class UMLCollabFactory extends AbstractUMLFactory {

    int edgeCounter = 1;

    void createEdge(Graph parent, Node fromNode, Node toNode, String meth) {
        String label = (edgeCounter++) + ":" + meth;
        Edge e = new Edge(UMLMetaType.COLLAB_EDGE, label, fromNode, toNode);
        e.addLabel(new EdgeLabel(UMLMetaType.COLLAB_EDGE_LABEL, label));
        parent.add(e);
    }

    public Graph createGraph() {
        return new Graph(UMLMetaType.COLLAB_GRAPH);
    }

    Node createNode_internal(Graph parent, String txt) {
        Node n = new Node(UMLMetaType.COLLAB_NODE, txt);
        n.addLabel(new NodeLabel(UMLMetaType.COLLAB_NODE_LABEL, txt));
        parent.add(n);
        return n;
    }

    Node createNode(Graph parent, String txt) {
        Node n = parent.getNode(txt);
        if (n == null) {
            return createNode_internal(parent, txt);
        } else {
            return n;
        }
    }

    public void createEdges(Graph graph, LinkedList<String> collabEdges) {
        Node prevNode = null;
        int i = 0;
        while (i < collabEdges.size()) {
            if (prevNode == null) {
                prevNode = createNode(graph, collabEdges.get(i++));
            } else {
                String meth = collabEdges.get(i++);
                String name = collabEdges.get(i++);
                Node n = createNode(graph, name);
                createEdge(graph, prevNode, n, meth);
                prevNode = n;
            }
        }
    }
}
