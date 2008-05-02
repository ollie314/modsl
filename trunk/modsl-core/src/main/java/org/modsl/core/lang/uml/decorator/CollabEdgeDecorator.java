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
