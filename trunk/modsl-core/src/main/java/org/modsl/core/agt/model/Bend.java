package org.modsl.core.agt.model;

import org.modsl.core.agt.visitor.AbstractVisitor;

public class Bend extends AbstractConnectedBox {


    @Override
    public void accept(AbstractVisitor visitor) {
        // TODO Auto-generated method stub

    }
    
    protected Edge parent;

    public Edge getParent() {
        return parent;
    }

    public void setParent(Edge parent) {
        this.parent = parent;
    }

    @Override
    public boolean isConnectedTo(AbstractConnectedBox p2) {
        if (parent.getDistance(this, p2) == 1) {
            return true;
        }
        return false;
    }
}
