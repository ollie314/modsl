package org.modsl.core.agt.layout;

import org.modsl.core.agt.model.Node;

public interface Layout {

    public void setConfig(LayoutConfig config);

    public void apply(Node<?> root);

}
