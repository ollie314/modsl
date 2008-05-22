package org.modsl.core.lang.uml.decorator.cls;

import static java.lang.Math.PI;

import org.modsl.core.lang.uml.UMLMetaType;
import org.modsl.core.lang.uml.decorator.AbstractArrowEdgeDecorator;

public class ClassAggregationEdgeDecorator extends AbstractArrowEdgeDecorator {

    @Override
    protected double getArrowAngle() {
        return PI / 5d;
    }

    @Override
    protected double getArrowLength() {
        return UMLMetaType.CLASS_AGGREGATION_EDGE.getConfig().getFt().getArrowLength();
    }

}
