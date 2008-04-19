package org.modsl.core.config;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LayoutPropsTest {

	@Test
	public void layout() {
		LP lp = new LP("/test_config", "test");
		assertEquals(lp.getProp("layout1"), "default1");
		assertEquals(lp.getProp("layout2"), "test2");
		assertEquals(lp.getProp("layout3"), "test3");
	}

	private static class LP extends AbstractLayoutProps {

		public LP(String path, String name) {
			super(path, name);
		}

	}

}
