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

    abstract protected double getArrowAngle();

    /**
     * @return position of the left arrow's side
     */
    public Pt getArrowLeft() {
        return getOffsetPoint(element.angle2() - getArrowAngle() / 2d, getArrowLength()); 
    }

    abstract protected double getArrowLength();

    /**
     * @return position of the middle point of the arrow
     */
    public Pt getArrowMiddle() {
        return getOffsetPoint(element.angle2(), getArrowLength() * cos(getArrowAngle() / 2d));
    }
    
    /**
     * @return position of the right arrow's side
     */
    public Pt getArrowRight() {
        return getOffsetPoint(element.angle2() + getArrowAngle() / 2d, getArrowLength()); 
    }

    protected Pt getOffsetPoint(double alpha, double len) {
        return new Pt(element.getNode2Port().x - len * cos(alpha), element.getNode2Port().y - len * sin(alpha));
    }

}