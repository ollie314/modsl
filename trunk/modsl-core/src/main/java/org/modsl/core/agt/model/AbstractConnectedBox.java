package org.modsl.core.agt.model;

public abstract class AbstractConnectedBox extends AbstractBox {

    public AbstractConnectedBox(MetaType type) {
        super(type);
        // TODO Auto-generated constructor stub
    }

    public AbstractConnectedBox(MetaType type, String name) {
        super(type, name);
    }

    public abstract boolean isConnectedTo(AbstractConnectedBox n2);
}
