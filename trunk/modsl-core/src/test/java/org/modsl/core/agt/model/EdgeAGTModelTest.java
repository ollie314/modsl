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
		assertEquals(PI / 4d, root.getEdge(0).angle2(), Pt.EPSILON); // 1-3
		assertEquals(PI * 3d / 4d, root.getEdge(1).angle2(), Pt.EPSILON); // 2-4
		// sign
		assertTrue(root.getEdge(2).angle2() > PI); // 3-7
		assertTrue(root.getEdge(2).angle2() < (3d / 2d) * PI);
		assertTrue(root.getEdge(3).angle2() > 3d / 2d * PI); // 4-6
		assertTrue(root.getEdge(3).angle2() < 2d * PI);
	}

	@Test
	public void cos() {
		// values for pi/4 and pi*3/4
		assertEquals(PI / 4d, acos(root.getEdge(0).cos2()), Pt.EPSILON); // 1-3
		assertEquals(PI * 3d / 4d, acos(root.getEdge(1).cos2()), Pt.EPSILON); // 2-4
		// sign
		assertTrue(root.getEdge(2).cos2() < 0); // 3-7
		assertTrue(root.getEdge(3).cos2() > 0); // 4-6
	}

	@Test
	public void sin() {
		// values for pi/4 and pi*3/4
		assertEquals(PI / 4d, asin(root.getEdge(0).sin2()), Pt.EPSILON); // 1-3
		assertEquals(PI * 3d / 4d, asin(root.getEdge(1).sin2()) + PI / 2d, Pt.EPSILON); // 2-4
		// sign
		assertTrue(root.getEdge(2).sin2() < 0); // 3-7
		assertTrue(root.getEdge(3).sin2() < 0); // 4-6
	}

	@Test
	public void tan() {
		// values for pi/4 and pi*3/4
		assertEquals(PI / 4d, atan(root.getEdge(0).tan2()), Pt.EPSILON); // 1-3
		assertEquals(PI * 3d / 4d, atan(root.getEdge(1).tan2()) + PI, Pt.EPSILON); // 2-4
		// sign
		assertTrue(root.getEdge(2).tan2() > 0); // 3-7
		assertTrue(root.getEdge(3).tan2() < 0); // 4-6
	}

}
