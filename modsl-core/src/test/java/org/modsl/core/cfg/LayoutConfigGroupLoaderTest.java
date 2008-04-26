package org.modsl.core.cfg;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LayoutConfigGroupLoaderTest {

    protected static final String CFGDIR = "test/props/ft";

    @Test
    public void configLoader() {
        LayoutConfigGroupLoader lcgl = new LayoutConfigGroupLoader(CFGDIR, TType.class);
        lcgl.load();
        assertEquals(0, TType.GRAPH.getLayouts().length);
        assertEquals(2, TType.NODE.getLayouts().length);
        assertEquals(1, ((T1Layout)TType.NODE.getLayouts()[0]).propMap.size());
        assertEquals(2, ((T2Layout)TType.NODE.getLayouts()[1]).propMap.size());
        assertEquals(1, TType.EDGE.getLayouts().length);
        assertEquals(3, ((T3Layout)TType.EDGE.getLayouts()[0]).propMap.size());
    }

}
