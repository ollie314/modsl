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
import org.modsl.core.agt.model.Graph;
import org.modsl.core.agt.model.Node;
import org.modsl.core.agt.model.NodeLabel;
import org.modsl.core.lang.uml.AbstractUMLFactory;
import org.modsl.core.lang.uml.UMLMetaType;

/**
 * Responsible for supporting the grammar when new abstract graph tree elements
 * need to be instantiated.
 * @author AVishnyakov
 */
public class UMLClassFactory extends AbstractUMLFactory {

	/**
	 * Create node from token
	 * @param parent parent
	 * @param token node token
	 * @return new node
	 */
	Node createNode(Graph parent, Token token, UMLMetaType nodeType, UMLMetaType labelType, List<NodeLabel> elements) {
		Node n = new Node(nodeType, token.getText());
		n.addLabel(new NodeLabel(labelType, token.getText()));
		n.addLabels(elements);
		parent.add(n);
		return n;
	}

	public Node createClassNode(Graph parent, Token token, List<NodeLabel> elements) {
		return createNode(parent, token, UMLMetaType.CLASS_CLASS_NODE, UMLMetaType.CLASS_CLASS_NODE_LABEL, elements);
	}

	public Node createInterfaceNode(Graph parent, Token token, List<NodeLabel> elements) {
		return createNode(parent, token, UMLMetaType.CLASS_INTERFACE_NODE, UMLMetaType.CLASS_INTERFACE_NODE_LABEL, elements);
	}

	public NodeLabel createNodeElement(UMLMetaType type, Token token) {
		return new NodeLabel(type, token.getText());
	}

	@Override
	public Graph createGraph() {
		return new Graph(UMLMetaType.CLASS_GRAPH);
	}

}
