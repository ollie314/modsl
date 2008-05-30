package org.modsl.core.agt.visitor;

import org.apache.log4j.Logger;
import org.modsl.core.agt.model.Bend;
import org.modsl.core.agt.model.Edge;
import org.modsl.core.agt.model.EdgeLabel;
import org.modsl.core.agt.model.Graph;
import org.modsl.core.agt.model.MetaType;
import org.modsl.core.agt.model.Node;
import org.modsl.core.agt.model.NodeLabel;

public abstract class AbstractMetaTypeVisitor extends AbstractVisitor {

    protected Logger log = Logger.getLogger(getClass());
    protected MetaType type;

    public void apply(Bend bend) {
        // to be overriden
    }

    public void apply(Edge edge) {
        // to be overriden
    }

    public void apply(EdgeLabel edgeLabel) {
        // to be overriden
    }

    public void apply(Graph graph) {
        // to be overriden
    }

    public void apply(Node node) {
        // to be overriden
    }

    public void apply(NodeLabel nodeLabel) {
        // to be overriden
    }

    @Override
    public void in(Bend bend) {
        if (type.equals(bend.getType())) {
            apply(bend);
        }
    }

    @Override
    public void in(Edge edge) {
        if (type.equals(edge.getType())) {
            apply(edge);
        }
    }

    @Override
    public void in(EdgeLabel label) {
        if (type.equals(label.getType())) {
            apply(label);
        }
    }

    @Override
    public void in(Graph graph) {
        if (type.equals(graph.getType())) {
            apply(graph);
        }
    }

    @Override
    public void in(Node node) {
        if (type.equals(node.getType())) {
            apply(node);
        }
    }

    @Override
    public void in(NodeLabel label) {
        if (type.equals(label.getType())) {
            apply(label);
        }
    }

    public AbstractMetaTypeVisitor type(MetaType type) {
        this.type = type;
        return this;
    }

}