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

package OBSOLETE;

import static org.junit.Assert.assertEquals;

import OBSOLETE.org.modsl.core.model.XY;

public class DiagramTest extends AbstractModelTest {

	public void recalcSize() {
		diag.recalcSize();
		assertEquals(1200d, diag.getSize().x, XY.EPSILON);
		assertEquals(1200d, diag.getSize().y, XY.EPSILON);
	}

	public void getMaxXY() {
		assertEquals(1200d, diag.getMaxXY().x, XY.EPSILON);
		assertEquals(1200d, diag.getMaxXY().y, XY.EPSILON);
	}

	public void getMinXY() {
		assertEquals(0d, diag.getMinXY().x, XY.EPSILON);
		assertEquals(0d, diag.getMinXY().y, XY.EPSILON);
	}

	public void rescale() {
		diag.setPaddingHeader(0d);
		diag.setPaddingFooter(0d);
		diag.setPaddingSides(0d);
		diag.rescale(new XY(1000d, 1000d));
		assertEquals(1000d, diag.getSize().x, XY.EPSILON);
		assertEquals(1000d, diag.getSize().y, XY.EPSILON);
		assertEquals(800d, diag.getElement("TopRight").getPosition().x, 1d);
		assertEquals(0d, diag.getElement("TopRight").getPosition().y, 1d);
		assertEquals(0d, diag.getElement("BottomLeft").getPosition().x, 1d);
		assertEquals(800d, diag.getElement("BottomLeft").getPosition().y, 1d);
		assertEquals(400d, diag.getElement("BottomMiddle").getPosition().x, 1d);
		assertEquals(880d, diag.getElement("BottomMiddle").getPosition().y, 1d);
		assertEquals(1000000d, diag.getArea(), 1d);
	}

	public void getArea() {
		diag.recalcSize();
		assertEquals(1440000d, diag.getArea(), XY.EPSILON);
	}

}
