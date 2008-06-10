package org.modsl.core.lang.uml.render.image;

import org.modsl.core.agt.model.Edge;

public class ClassImplementsArrowEdgeRenderVisitor extends ClassExtendsArrowEdgeRenderVisitor {

    @Override
    public void apply(Edge e) {
        g.setStroke(DASHED_STROKE);
        draw(e, getMidPoint(e));
        g.setStroke(NORMAL_STROKE);
        drawSides(e);
        g.setStroke(NORMAL_STROKE);
        drawButt(e);    }

}
