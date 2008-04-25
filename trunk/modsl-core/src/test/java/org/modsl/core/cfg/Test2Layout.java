package org.modsl.core.cfg;

import java.util.Map;

import org.modsl.core.agt.layout.Layout;
import org.modsl.core.agt.model.Node;

public class Test2Layout implements Layout {

    protected Map<String, String> propMap;

   @Override
    public void apply(Node<?> root) {
        // TODO Auto-generated method stub
    }

    @Override
    public String getConfigName() {
        return "test2_layout_config";
    }

    @Override
    public void setConfig(Map<String, String> propMap) {
        this.propMap = propMap;
    }


}
