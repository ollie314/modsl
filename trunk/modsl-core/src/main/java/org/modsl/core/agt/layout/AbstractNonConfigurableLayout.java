package org.modsl.core.agt.layout;

import java.util.Map;

public abstract class AbstractNonConfigurableLayout implements Layout {

    @Override
    public String getConfigName() {
        return null; // non configurable
    }

    @Override
    public void setConfig(Map<String, String> propMap) {
        // none
    }

}
