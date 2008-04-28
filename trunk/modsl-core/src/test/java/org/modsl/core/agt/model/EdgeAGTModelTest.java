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

package org.modsl.core.agt.model;

import static java.lang.Math.PI;
import static java.lang.Math.acos;
import static java.lang.Math.asin;
import static java.lang.Math.atan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EdgeAGTModelTest extends AbstractAGTModelTest {
	
	@Test
	public void angle() {
		assertEquals(PI / 4d, root.getEdge(0).angle(), Pt.EPSILON); // 1-3
		assertEquals(PI * 3d / 4d, root.getEdge(1).angle(), Pt.EPSILON); // 2-4
		// sign
		assertTrue(root.getEdge(2).angle() > PI); // 3-7
		assertTrue(root.getEdge(2).angle() < (3d / 2d) * PI);
		assertTrue(root.getEdge(3).angle() > 3d / 2d * PI); // 4-6
		assertTrue(root.getEdge(3).angle() < 2d * PI);
	}

	@Test
	public void cos() {
		// values for pi/4 and pi*3/4
		assertEquals(PI / 4d, acos(root.getEdge(0).cos()), Pt.EPSILON); // 1-3
		assertEquals(PI * 3d / 4d, acos(root.getEdge(1).cos()), Pt.EPSILON); // 2-4
		// sign
		assertTrue(root.getEdge(2).cos() < 0); // 3-7
		assertTrue(root.getEdge(3).cos() > 0); // 4-6
	}

	@Test
	public void sin() {
		// values for pi/4 and pi*3/4
		assertEquals(PI / 4d, asin(root.getEdge(0).sin()), Pt.EPSILON); // 1-3
		assertEquals(PI * 3d / 4d, asin(root.getEdge(1).sin()) + PI / 2d, Pt.EPSILON); // 2-4
		// sign
		assertTrue(root.getEdge(2).sin() < 0); // 3-7
		assertTrue(root.getEdge(3).sin() < 0); // 4-6
	}

	@Test
	public void tan() {
		// values for pi/4 and pi*3/4
		assertEquals(PI / 4d, atan(root.getEdge(0).tan()), Pt.EPSILON); // 1-3
		assertEquals(PI * 3d / 4d, atan(root.getEdge(1).tan()) + PI, Pt.EPSILON); // 2-4
		// sign
		assertTrue(root.getEdge(2).tan() > 0); // 3-7
		assertTrue(root.getEdge(3).tan() < 0); // 4-6
	}

}
