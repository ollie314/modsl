package org.modsl.core.agt.visitor;

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

    @Override
    public void in(Edge<T> edge) {
        // TODO
    }

    @Override
    public void in(Node<T> node) {
        // callTemplate(node, SUFF_IN);
    }

    @Override
    public void out(Edge<T> edge) {
        // TODO
    }

    @Override
    public void out(Node<T> node) {
        // callTemplate(node, SUFF_OUT);
    }

}
