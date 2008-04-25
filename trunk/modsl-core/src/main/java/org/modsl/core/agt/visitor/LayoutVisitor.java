package org.modsl.core.agt.visitor;

import org.modsl.core.agt.layout.Layout;
import org.modsl.core.agt.model.Edge;
import org.modsl.core.agt.model.MetaType;
import org.modsl.core.agt.model.Node;
import org.modsl.core.cfg.FontTransformLoader;

public class LayoutVisitor<T extends MetaType> extends AbstractVisitor<T> {

    protected Class<T> metaTypeClass;

    public LayoutVisitor(String cfgdir, String name, Class<T> metaTypeClass) {
        this.metaTypeClass = metaTypeClass;
        new FontTransformLoader(cfgdir, name, metaTypeClass).load();
    }

    private void apply(Edge<?> edge, Layout[] layouts) {
        for (Layout l : layouts) {
            l.apply(edge);
        }
    }

    private void apply(Node<?> node, Layout[] layouts) {
        for (Layout l : layouts) {
            l.apply(node);
        }
    }

    @Override
    public void out(Edge<T> edge) {
        apply(edge, edge.getType().getLayouts());
    }

    @Override
    public void out(Node<T> node) {
        apply(node, node.getType().getLayouts());
    }

}
