package org.modsl.core.cfg;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LayoutConfigGroupLoaderTest {

    protected static final String CFGDIR = "test/props/ft";

    @Test
    public void configLoader() {
        LayoutConfigGroupLoader lcgl = new LayoutConfigGroupLoader(CFGDIR, TestType.class);
        lcgl.load();
        assertEquals(0, TestType.GRAPH.getLayouts().length);
        assertEquals(2, TestType.NODE.getLayouts().length);
        assertEquals(1, ((Test1Layout)TestType.NODE.getLayouts()[0]).propMap.size());
        assertEquals(2, ((Test2Layout)TestType.NODE.getLayouts()[1]).propMap.size());
        assertEquals(1, TestType.EDGE.getLayouts().length);
        assertEquals(3, ((Test3Layout)TestType.EDGE.getLayouts()[0]).propMap.size());
    }

}
