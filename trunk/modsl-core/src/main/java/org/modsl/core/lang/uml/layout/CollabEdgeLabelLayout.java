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

package org.modsl.core.lang.uml.layout;

import static java.lang.Math.max;
import static java.lang.Math.min;

import org.modsl.core.agt.common.FontTransform;
import org.modsl.core.agt.layout.AbstractNonConfigurableLayoutVisitor;
import org.modsl.core.agt.model.AbstractBox;
import org.modsl.core.agt.model.Edge;
import org.modsl.core.agt.model.Label;
import org.modsl.core.agt.model.MetaType;
import org.modsl.core.agt.model.Node;
import org.modsl.core.agt.model.Pt;

/**
 * Edge label placement
 * @author avishnyakov
 */
public class CollabEdgeLabelLayout extends AbstractNonConfigurableLayoutVisitor {

	public CollabEdgeLabelLayout(MetaType type) {
		super(type);
	}

	@Override
	public void apply(Label label) {
		Edge edge = (Edge) label.getParent();
		Pt midPoint = getMidPoint(edge);
		FontTransform ft = edge.getType().getConfig().getFt();
		label.setSize(new Pt(ft.getExtStringWidth(edge.getName()), ft.getExtHeight(1)));
		label.setPos(midPoint.minus(new Pt(label.getSize().x / 2d, label.getSize().y / 2d)));

	}

	/**
	 * @return position of the connector's midpoint
	 */
	public Pt getMidPoint(Edge edge) {
		// return
		// parent.getNode1().getCtrPos().plus(parent.getNode2().getCtrPos().minus(parent.getNode1().getCtrPos()).div(2d));
		Node n1 = edge.getNode1();
		Node n2 = edge.getNode2();
		double ratio = 1d / 2d; // 1d * n1.getOutDegree() / (n1.getOutDegree() +
								// n2.getInDegree());
		ratio = min(2d / 3d, max(ratio, 1d / 3d)); // TODO
		AbstractBox<?> b1 = n1;
		AbstractBox<?> b2 = n2;
		if (edge.getBends().size() > 0) {
			if (n1.getOutDegree() > n2.getInDegree()) {
				b1 = edge.getLastBend();
			} else {
				b2 = edge.getFirstBend();
			}
		}
		return b1.getCtrPos().plus(b2.getCtrPos().minus(b1.getCtrPos()).mulBy(ratio));
	}

}
