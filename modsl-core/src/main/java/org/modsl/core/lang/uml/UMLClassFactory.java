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

import org.antlr.runtime.Token;
import org.modsl.core.agt.model.Graph;
import org.modsl.core.agt.model.Node;
import org.modsl.core.agt.model.NodeLabel;

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
	public Node createNode(Graph parent, Token token) {
		Node n = new Node(UMLMetaType.CLASS_CLASS_NODE, token.getText());
		n.addLabel(new NodeLabel(UMLMetaType.CLASS_NODE_LABEL, token.getText()));
		parent.add(n);
		return n;
	}

	@Override
	public Graph createGraph() {
		return new Graph(UMLMetaType.CLASS_GRAPH);
	}

}
