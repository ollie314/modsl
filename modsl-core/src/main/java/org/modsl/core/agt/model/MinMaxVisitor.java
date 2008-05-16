package org.modsl.core.agt.model;

import org.modsl.core.agt.visitor.AbstractVisitor;

/**
 * Recursive position calculations etc on the graph
 * @author avishnyakov
 */
abstract class MinMaxVisitor extends AbstractVisitor {

    Pt p;

    MinMaxVisitor(Pt init) {
        this.p = init;
    }

    public MinMaxVisitor() {
    }

    @Override
    public void in(Bend bend) {
        apply(bend);
    }

    @Override
    public void in(Graph graph) {
        apply(graph);
    }

    @Override
    public void in(Label<?> label) {
        apply(label);
    }

    @Override
    public void in(Node node) {
        apply(node);
    }

    abstract void apply(AbstractBox<?> b);

}
