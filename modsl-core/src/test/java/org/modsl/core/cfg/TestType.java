/**
 * 
 */
package org.modsl.core.cfg;

import org.modsl.core.agt.layout.Layout;
import org.modsl.core.agt.model.FontTransform;
import org.modsl.core.agt.model.MetaType;

public enum TestType implements MetaType {

    GRAPH, NODE(new Layout[] { new Test1Layout(), new Test2Layout() }), EDGE(new Layout[] { new Test3Layout() });

    protected FontTransform fontTransform;
    protected Layout[] layouts;
    
    private TestType() {
        this.layouts = new Layout[] {};
    }

    private TestType(Layout[] layouts) {
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