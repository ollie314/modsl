package org.modsl.core.config;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TemplatePropsTest {

	@Test
	public void layout() {
		TP lp = new TP("/config", "test");
		assertEquals(lp.getProp("template1"), "default1");
		assertEquals(lp.getProp("template2"), "test2");
		assertEquals(lp.getProp("template3"), "test3");
	}

	private static class TP extends AbstractTemplateProps {

		public TP(String path, String name) {
			super(path, name);
		}

	}

}
