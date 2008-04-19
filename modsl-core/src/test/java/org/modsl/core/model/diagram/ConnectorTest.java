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

package org.modsl.core.model.diagram;

import static java.lang.Math.PI;
import static java.lang.Math.acos;
import static java.lang.Math.asin;
import static java.lang.Math.atan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.modsl.core.model.XY;

public class ConnectorTest extends AbstractModelTest {

	@Test
	public void angle() {
		assertEquals(PI / 4d, diag.getConnectors().get(0).angle(), XY.EPSILON); // 1-3
		assertEquals(PI * 3d / 4d, diag.getConnectors().get(1).angle(), XY.EPSILON); // 2-4
		// sign
		assertTrue(diag.getConnectors().get(2).angle() > PI); // 3-7
		assertTrue(diag.getConnectors().get(2).angle() < (3d / 2d) * PI);
		assertTrue(diag.getConnectors().get(3).angle() > 3d / 2d * PI); // 4-6
		assertTrue(diag.getConnectors().get(3).angle() < 2d * PI);
	}

	@Test
	public void cos() {
		// values for pi/4 and pi*3/4
		assertEquals(PI / 4d, acos(diag.getConnectors().get(0).cos()), XY.EPSILON); // 1-3
		assertEquals(PI * 3d / 4d, acos(diag.getConnectors().get(1).cos()), XY.EPSILON); // 2-4
		// sign
		assertTrue(diag.getConnectors().get(2).cos() < 0); // 3-7
		assertTrue(diag.getConnectors().get(3).cos() > 0); // 4-6
	}

	@Test
	public void sin() {
		// values for pi/4 and pi*3/4
		assertEquals(PI / 4d, asin(diag.getConnectors().get(0).sin()), XY.EPSILON); // 1-3
		assertEquals(PI * 3d / 4d, asin(diag.getConnectors().get(1).sin()) + PI / 2d, XY.EPSILON); // 2-4
		// sign
		assertTrue(diag.getConnectors().get(2).sin() < 0); // 3-7
		assertTrue(diag.getConnectors().get(3).sin() < 0); // 4-6
	}

	@Test
	public void tan() {
		// values for pi/4 and pi*3/4
		assertEquals(PI / 4d, atan(diag.getConnectors().get(0).tan()), XY.EPSILON); // 1-3
		assertEquals(PI * 3d / 4d, atan(diag.getConnectors().get(1).tan()) + PI, XY.EPSILON); // 2-4
		// sign
		assertTrue(diag.getConnectors().get(2).tan() > 0); // 3-7
		assertTrue(diag.getConnectors().get(3).tan() < 0); // 4-6
	}

}
