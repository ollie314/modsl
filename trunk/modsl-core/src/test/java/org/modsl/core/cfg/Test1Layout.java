package org.modsl.core.cfg;

import java.util.Map;

import org.modsl.core.agt.layout.Layout;
import org.modsl.core.agt.model.Edge;
import org.modsl.core.agt.model.Node;

public class Test1Layout implements Layout {

    protected Map<String, String> propMap;

    @Override
    public String getConfigName() {
        return "test1_layout_config";
    }

    @Override
    public void setConfig(Map<String, String> propMap) {
        this.propMap = propMap;
    }

    @Override
    public void apply(Edge<?> edge) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void apply(Node<?> node) {
        // TODO Auto-generated method stub
        
    }

}