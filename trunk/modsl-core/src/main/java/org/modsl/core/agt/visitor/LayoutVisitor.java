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

package org.modsl.core.agt.visitor;

import org.modsl.core.agt.layout.AbstractLayout;
import org.modsl.core.agt.model.Edge;
import org.modsl.core.agt.model.MetaType;
import org.modsl.core.agt.model.Node;

public class LayoutVisitor<T extends MetaType> extends AbstractVisitor<T> {

	private void apply(Edge<?> edge, AbstractLayout[] layouts) {
		for (AbstractLayout l : layouts) {
			l.apply(edge);
		}
	}

	private void apply(Node<?> node, AbstractLayout[] layouts) {
		for (AbstractLayout l : layouts) {
			l.apply(node);
		}
	}

	@Override
	public void out(Edge<T> edge) {
		apply(edge, edge.getType().getConfig().getLayouts());
	}

	@Override
	public void out(Node<T> node) {
		apply(node, node.getType().getConfig().getLayouts());
	}

}
