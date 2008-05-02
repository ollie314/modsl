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

package org.modsl.core.lang.uml.decorator;

import static java.lang.Math.PI;

import org.modsl.core.agt.decor.AbstractDecorator;
import org.modsl.core.agt.model.Edge;
import org.modsl.core.agt.model.Pt;
import org.modsl.core.lang.uml.UMLMetaType;

public class CollabEdgeDecorator extends AbstractDecorator<Edge<?>> {

	protected double arrowAngle = PI / 5d;

	protected double arrowLength;

	@Override
	public void decorate(Edge<?> parent) {
		super.decorate(parent);
		arrowLength = UMLMetaType.COLLAB_EDGE.getConfig().getFontTransform().getArrowLength();
	}

	/**
	 * @return position of the left arrow's side
	 */
	public Pt getArrowLeft() {
		double alpha = parent.angle() - arrowAngle / 2d;
		return new Pt(parent.getNode2Clip().x - arrowLength * Math.cos(alpha), parent.getNode2Clip().y - arrowLength * Math.sin(alpha));
	}

	/**
	 * @return position of the right arrow's side
	 */
	public Pt getArrowRight() {
		double alpha = parent.angle() + arrowAngle / 2d;
		return new Pt(parent.getNode2Clip().x - arrowLength * Math.cos(alpha), parent.getNode2Clip().y - arrowLength * Math.sin(alpha));
	}

	/**
	 * @return position of the connector's midpoint
	 */
	public Pt getMidPoint() {
		return parent.getNode1().getCtrPos().plus(parent.getNode2().getCtrPos().minus(parent.getNode1().getCtrPos()).div(2d));
	}

}
