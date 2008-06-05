package org.modsl.core.lang.uml.render.image;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

import org.modsl.core.agt.model.Edge;
import org.modsl.core.agt.model.Pt;
import org.modsl.core.agt.render.image.EdgeRenderVisitor;

public abstract class AbstractArrowEdgeRenderVisitor extends EdgeRenderVisitor {

    @Override
    public void apply(Edge e) {
        super.apply(e);
        drawSides(e);
    }
    
    protected void drawSides(Edge e) {
        Pt offl = getOffsetPoint(e, e.angle2() - getArrowAngle() / 2d, getArrowLength());
        g.drawLine((int) offl.x, (int) offl.y, (int) e.getNode2Port().x, (int) e.getNode2Port().y);
        Pt offr = getOffsetPoint(e, e.angle2() + getArrowAngle() / 2d, getArrowLength());
        g.drawLine((int) offr.x, (int) offr.y, (int) e.getNode2Port().x, (int) e.getNode2Port().y);
    }

    //return getOffsetPoint(element.angle2(), getArrowLength() * cos(getArrowAngle() / 2d));

    protected abstract double getArrowAngle();

    abstract protected double getArrowLength();

    protected Pt getOffsetPoint(Edge e, double alpha, double len) {
        return new Pt(e.getNode2Port().x - len * cos(alpha), e.getNode2Port().y - len * sin(alpha));
    }

}
