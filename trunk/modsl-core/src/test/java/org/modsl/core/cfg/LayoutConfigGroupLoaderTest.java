package org.modsl.core.cfg;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LayoutConfigGroupLoaderTest {

	protected static final String CFGDIR = "test/props/ft";

	@Test
	public void configLoader() {
		TLayoutGroupLoader tgl = new TLayoutGroupLoader(CFGDIR, TType.class);
		tgl.load();
		assertEquals(0, TType.GRAPH.getConfig().getLayouts().length);
		assertEquals(2, TType.NODE.getConfig().getLayouts().length);
		assertEquals(1, ((T1Layout) TType.NODE.getConfig().getLayouts()[0]).propMap.size());
		assertEquals(2, ((T2Layout) TType.NODE.getConfig().getLayouts()[1]).propMap.size());
		assertEquals(1, TType.EDGE.getConfig().getLayouts().length);
		assertEquals(3, ((T3Layout) TType.EDGE.getConfig().getLayouts()[0]).propMap.size());
	}

}
