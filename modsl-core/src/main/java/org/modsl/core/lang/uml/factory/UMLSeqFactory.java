package org.modsl.core.lang.uml.factory;

import org.modsl.core.lang.uml.UMLMetaType;

public class UMLSeqFactory extends AbstractUMLSeqCollabFactory {

    @Override
    UMLMetaType getEdgeLabelType() {
        return UMLMetaType.SEQ_EDGE_LABEL;
    }

    @Override
    UMLMetaType getEdgeType() {
        return UMLMetaType.SEQ_EDGE;
    }

    @Override
    UMLMetaType getGraphType() {
        return UMLMetaType.SEQ_GRAPH;
    }

    @Override
    UMLMetaType getNodeLabelType() {
        return UMLMetaType.SEQ_NODE_LABEL;
    }

    @Override
    UMLMetaType getNodeType() {
        return UMLMetaType.SEQ_NODE;
    }

}
