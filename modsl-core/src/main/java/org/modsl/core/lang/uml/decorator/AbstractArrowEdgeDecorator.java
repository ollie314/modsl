package org.modsl.core.lang.uml.decorator;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

import org.modsl.core.agt.decor.AbstractDecorator;
import org.modsl.core.agt.model.Edge;
import org.modsl.core.agt.model.Pt;

public abstract class AbstractArrowEdgeDecorator extends AbstractDecorator<Edge> {

    public AbstractArrowEdgeDecorator() {
        super();
    }

    @Override
    public void decorate(Edge parent) {
        super.decorate(parent);
    }

    /**
     * @return position of the left arrow's side
     */
    public Pt getArrowLeft() {
        double alpha = element.angle2() - getArrowAngle() / 2d;
        return new Pt(element.getNode2Port().x - getArrowLength() * cos(alpha), element.getNode2Port().y - getArrowLength()
                * sin(alpha));
    }

    /**
     * @return position of the right arrow's side
     */
    public Pt getArrowRight() {
        double alpha = element.angle2() + getArrowAngle() / 2d;
        return new Pt(element.getNode2Port().x - getArrowLength() * cos(alpha), element.getNode2Port().y - getArrowLength()
                * sin(alpha));
    }

    abstract protected double getArrowAngle();

    abstract protected double getArrowLength();

}