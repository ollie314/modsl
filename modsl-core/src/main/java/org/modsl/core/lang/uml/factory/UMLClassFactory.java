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

import java.util.List;

import org.antlr.runtime.Token;
import org.modsl.core.agt.model.Edge;
import org.modsl.core.agt.model.Graph;
import org.modsl.core.agt.model.Node;
import org.modsl.core.agt.model.NodeLabel;
import org.modsl.core.lang.uml.UMLMetaType;

/**
 * Responsible for supporting the grammar when new abstract graph tree elements
 * need to be instantiated.
 * @author AVishnyakov
 */
public class UMLClassFactory extends AbstractUMLFactory {

    void createNode(Graph parent, Token token, UMLMetaType nodeType, UMLMetaType labelType, List<NodeLabel> elements,
            List<Token> extendTokens, List<Token> implementTokens) {

        Node n = new Node(nodeType, token.getText());

        n.addLabel(new NodeLabel(labelType, token.getText()));
        n.addLabels(elements);
        parent.add(n);

        if (extendTokens != null) {
            for (Token etoken : extendTokens) {
                Edge e = new Edge(UMLMetaType.CLASS_EXTENDS_EDGE, n, etoken.getText());
                parent.add(e);
            }
        }
        
        if (implementTokens != null) {
            for (Token itoken : implementTokens) {
                Edge e = new Edge(UMLMetaType.CLASS_IMPLEMENTS_EDGE, n, itoken.getText());
                parent.add(e);
            }
        }

    }

    public void createClassNode(Graph parent, Token token, List<NodeLabel> elements, List<Token> extendTokens,
            List<Token> implementTokens) {
        createNode(parent, token, UMLMetaType.CLASS_CLASS_NODE, UMLMetaType.CLASS_CLASS_NODE_LABEL, elements, extendTokens,
                implementTokens);
    }

    public void createInterfaceNode(Graph parent, Token token, List<NodeLabel> elements, List<Token> extendTokens) {
        createNode(parent, token, UMLMetaType.CLASS_INTERFACE_NODE, UMLMetaType.CLASS_INTERFACE_NODE_LABEL, elements, extendTokens,
                null);
    }

    public NodeLabel createNodeElement(UMLMetaType type, String text) {
        return new NodeLabel(type, text);
    }

    @Override
    public Graph createGraph() {
        return new Graph(UMLMetaType.CLASS_GRAPH);
    }

}
