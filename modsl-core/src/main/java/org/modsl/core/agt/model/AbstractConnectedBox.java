package org.modsl.core.agt.model;

public abstract class AbstractConnectedBox extends AbstractBox {

    public AbstractConnectedBox() {
        //
    }
    
    public AbstractConnectedBox(MetaType type) {
        super(type);
    }

    public AbstractConnectedBox(MetaType type, String name) {
        super(type, name);
    }

    public abstract boolean isConnectedTo(AbstractConnectedBox n2);
}
