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

package org.modsl.core.lang.uml;

import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.Token;
import org.modsl.core.agt.model.Edge;
import org.modsl.core.agt.model.Graph;
import org.modsl.core.agt.model.NodeLabel;
import org.modsl.core.agt.model.Node;

/**
 * Responsible for supporting the grammar when new abstract graph tree elements
 * need to be instantiated.
 * @author AVishnyakov
 */
public class UMLCollabFactory extends AbstractUMLFactory {

    protected int edgeCounter = 1;

    /**
     * Create list of edges given list of edge tokens
     * @param parent parent node the edges belong to
     * @param ctokens edge tokens
     * @return list of edges
     */
    public List<Edge> createEdges(Graph parent, List<Token> ctokens, List<Token> mtokens) {

        List<Edge> es = new LinkedList<Edge>();

        Node n1 = null, n2 = null;
        Token t1, t2;

        for (int i = 0; i < ctokens.size() - 1; i++) {

            t1 = ctokens.get(i);
            t2 = ctokens.get(i + 1);

            if (i == 0) {
                n1 = createNodeIfDoesntExist(parent, t1);
            }
            n2 = createNodeIfDoesntExist(parent, t2);

            String label = (edgeCounter++) + ":" + mtokens.get(i).getText();
            Edge e = new Edge(UMLMetaType.COLLAB_EDGE, label, n1, n2);
            e.addLabel(new NodeLabel(UMLMetaType.COLLAB_EDGE_LABEL, label));
            parent.add(e);
            es.add(e);

            n1 = n2; // << shift

        }

        return es;

    }

    /**
     * Create node from token
     * @param parent parent
     * @param token node token
     * @return new node
     */
    private Node createNode(Graph parent, Token token) {
        Node n = new Node(UMLMetaType.COLLAB_NODE, token.getText());
        parent.add(n);
        return n;
    }

    /**
     * Will create node by given name if the node with such name doesn't exist
     * yet
     * @param parent parent node
     * @param token new node token
     * @return node
     */
    private Node createNodeIfDoesntExist(Graph parent, Token token) {
        Node n = parent.getNode(token.getText());
        if (n == null) {
            return createNode(parent, token);
        } else {
            return n;
        }
    }

    @Override
    public Graph createGraph() {
        return new Graph(UMLMetaType.COLLAB_GRAPH);
    }

}
