package org.modsl.core.agt.render.image;

import java.awt.geom.Line2D;

import org.modsl.core.agt.model.Edge;
import org.modsl.core.render.Style;

public class EdgeRenderVisitor extends AbstractRenderVisitor {

    @Override
    public void apply(Edge e) {
        Style s = e.getType().getStyle();
        g.setColor(s.getStrokeColor());
        g.draw(new Line2D.Double(e.getNode1Port().x, e.getNode1Port().y, e.getNode2Port().x, e.getNode2Port().y));
    }
}
