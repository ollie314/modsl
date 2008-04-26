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

package org.modsl.core.cfg;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LayoutConfigGroupLoaderTest {

	protected static final String CFGDIR = "test/props/ft";

	@Test
	public void configLoader() {
		TLayoutGroupLoader tgl = new TLayoutGroupLoader(CFGDIR, TMetaType.class);
		tgl.load();
		assertEquals(0, TMetaType.GRAPH.getConfig().getLayouts().length);
		assertEquals(2, TMetaType.NODE.getConfig().getLayouts().length);
		assertEquals(1, ((T1Layout) TMetaType.NODE.getConfig().getLayouts()[0]).propMap.size());
		assertEquals(2, ((T2Layout) TMetaType.NODE.getConfig().getLayouts()[1]).propMap.size());
		assertEquals(1, TMetaType.EDGE.getConfig().getLayouts().length);
		assertEquals(3, ((T3Layout) TMetaType.EDGE.getConfig().getLayouts()[0]).propMap.size());
	}

}
