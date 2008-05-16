package org.modsl.core.agt.model;

import org.modsl.core.agt.visitor.AbstractVisitor;

public class EdgeLabel extends Label<Edge> {

    AbstractBox<?> anchor1;
    AbstractBox<?> anchor2;

    Pt offset = new Pt(0d, 0d);

    public EdgeLabel(MetaType type, String name) {
        super(type, name);
    }

    @Override
    public void accept(AbstractVisitor visitor) {
        visitor.in(this);
        visitor.out(this);
    }

    public AbstractBox<?> getAnchor1() {
        return anchor1;
    }

    public AbstractBox<?> getAnchor2() {
        return anchor2;
    }

    public Pt getOffset() {
        return offset;
    }

    @Override
    public Pt getPos() {
        Pt mid = anchor1.getCtrPos().plus(anchor2.getCtrPos().minus(anchor1.getCtrPos()).mulBy(0.5d));
        mid.incBy(new Pt(offset.y / anchor1.tan(anchor2), offset.y));
        mid.decBy(new Pt(getSize().x / 2d, getSize().y / 2d));
        return mid;
    }

    public void setAnchor1(AbstractBox<?> anchor1) {
        this.anchor1 = anchor1;
    }

    public void setAnchor2(AbstractBox<?> anchor2) {
        this.anchor2 = anchor2;
    }

    public void setOffset(double x, double y) {
        this.offset.x = x;
        this.offset.x = y;
    }

}
