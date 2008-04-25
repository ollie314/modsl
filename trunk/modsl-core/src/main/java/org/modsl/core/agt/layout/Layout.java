package org.modsl.core.agt.layout;

import org.modsl.core.agt.model.MetaType;
import org.modsl.core.agt.model.Node;

public interface Layout<T extends MetaType> {
    
    public void apply(Node<T> root);

}
