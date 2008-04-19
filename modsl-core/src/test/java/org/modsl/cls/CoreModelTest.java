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

package org.modsl.cls;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.modsl.cls.model.ClassDiagram;
import org.modsl.core.ModslProcessor;

public class CoreModelTest {

	@Before
	public void setUp() {
		ModslProcessor.init();
	}

	@Test
	public void coreModel() {
		ClassDiagram d = ModslProcessor.getClassDiagramProcessor().process("CoreModel");
		assertTrue(d.getElement("Graph").getSize().x > 50);
		assertTrue(d.getElement("Graph").getSize().y > 20);
		assertTrue(d.getSize().x > 0);
		assertTrue(d.getSize().y > 0);
	}

}
