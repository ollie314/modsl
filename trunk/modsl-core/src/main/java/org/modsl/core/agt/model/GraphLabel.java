package org.modsl.core.agt.model;

import org.modsl.core.agt.visitor.AbstractVisitor;

public class GraphLabel extends Label<Graph> {

    Pt offset = new Pt(0d, 0d);

    public GraphLabel(MetaType type, String name) {
        super(type, name);
    }

    @Override
    public void accept(AbstractVisitor visitor) {
        visitor.in(this);
        visitor.out(this);
    }

    public Pt getOffset() {
        return offset;
    }

    @Override
    public Pt getPos() {
        return getParent().getPos().plus(offset);
    }

}
