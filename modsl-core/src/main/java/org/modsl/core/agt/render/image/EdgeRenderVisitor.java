package org.modsl.core.agt.render.image;

import org.modsl.core.agt.model.Edge;
import org.modsl.core.render.Style;

public class EdgeRenderVisitor extends AbstractRenderVisitor {

    @Override
    public void apply(Edge e) {
        Style s = e.getType().getStyle();
        g.setColor(s.getStrokeColor());
        g.drawLine((int) e.getNode1Port().x, (int) e.getNode1Port().y, (int) e.getNode2Port().x, (int) e.getNode2Port().y);
    }
}
