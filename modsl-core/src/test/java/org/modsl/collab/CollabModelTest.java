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

package org.modsl.collab;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.modsl.collab.model.CollabDiagram;
import org.modsl.core.ModslProcessor;

public class CollabModelTest {

	@Before
	public void setUp() {
		ModslProcessor.init();
	}

	@Test
	public void coreModel() {
		CollabDiagram d = ModslProcessor.getCollabDiagramProcessor().process("CollabModel");
		assertNotNull(d.getElement("Class2:Object2"));
	}

}
