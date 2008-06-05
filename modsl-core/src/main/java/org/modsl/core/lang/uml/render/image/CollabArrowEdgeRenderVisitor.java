package org.modsl.core.lang.uml.render.image;

import static java.lang.Math.PI;

import org.modsl.core.lang.uml.UMLMetaType;

public class CollabArrowEdgeRenderVisitor extends AbstractArrowEdgeRenderVisitor {

    @Override
    protected double getArrowAngle() {
        return PI / 5d;
    }

    @Override
    protected double getArrowLength() {
        return UMLMetaType.COLLAB_EDGE.getStyle().getArrowLength();
    }

}
