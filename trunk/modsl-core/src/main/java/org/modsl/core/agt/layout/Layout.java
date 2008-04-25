package org.modsl.core.agt.layout;

import java.util.Map;

import org.modsl.core.agt.model.Node;

public interface Layout {

    public String getConfigName();

    public void setConfig(Map<String, String> propMap);

    public void apply(Node<?> root);

}
