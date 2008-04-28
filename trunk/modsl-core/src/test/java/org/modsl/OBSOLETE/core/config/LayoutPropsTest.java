/**
 * Copyright 2008 Andrew Vishnyakov <avishn@gmail.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */

package org.modsl.OBSOLETE.core.config;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.modsl.core.config.AbstractLayoutProps;

public class LayoutPropsTest {


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
