package org.modsl.core.cfg;

import org.modsl.core.agt.layout.Layout;
import org.modsl.core.agt.model.MetaType;

public class LayoutConfigGroupLoader {

    protected Class<? extends MetaType> metaTypeClass;
    protected String path;

    public LayoutConfigGroupLoader(String path, Class<? extends MetaType> metaTypeClass) {
        this.metaTypeClass = metaTypeClass;
    }

    public void load() {
        for (MetaType mt : metaTypeClass.getEnumConstants()) {
            for (Layout l : mt.getLayouts()) {
                if (l.getConfigName() != null) {
                    PropLoader pl = new PropLoader(path, l.getConfigName(), true);
                    l.setConfig(pl.getProps());
                }
            }
        }
    }

}
