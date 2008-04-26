/**
 * 
 */
package org.modsl.core.cfg;

import org.modsl.core.agt.layout.Layout;
import org.modsl.core.agt.model.FontTransform;
import org.modsl.core.agt.model.MetaType;

public enum TType implements MetaType {

    GRAPH, NODE(new Layout[] { new T1Layout(), new T2Layout() }), EDGE(new Layout[] { new T3Layout() });

    protected FontTransform fontTransform;
    protected Layout[] layouts;
    
    private TType() {
        this.layouts = new Layout[] {};
    }

    private TType(Layout[] layouts) {
        this.layouts = layouts;
    }

    @Override
    public FontTransform getFontTransform() {
        return fontTransform;
    }

    @Override
    public void setFontTransform(FontTransform fontTransform) {
        this.fontTransform = fontTransform;
    }

    @Override
    public Layout[] getLayouts() {
        return layouts;
    }
    
}