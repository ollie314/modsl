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

import org.modsl.core.model.XY;

public abstract class AbstractModelTest {

	Diagram<Object, Element, Connector> diag;

	protected void setUp() throws Exception {

		diag = new Diagram<Object, Element, Connector>("_diagram", null, null);

		Element<Diagram, ElementDetail> e1 = new Element<Diagram, ElementDetail>("_class", diag, "TopLeft");
		e1.setPosition(new XY(0d, 0d));
		e1.setSize(new XY(10d, 10d));

		Element<Diagram, ElementDetail> e2 = new Element<Diagram, ElementDetail>("_class", diag, "TopRight");
		e2.setPosition(new XY(1000d, 0d));
		e2.setSize(new XY(200d, 50d));

		Element<Diagram, ElementDetail> e3 = new Element<Diagram, ElementDetail>("_class", diag, "BottomRight");
		e3.setPosition(new XY(1000d, 1000d));
		e3.setSize(new XY(10d, 10d));

		Element<Diagram, ElementDetail> e4 = new Element<Diagram, ElementDetail>("_class", diag, "BottomLeft");
		e4.setPosition(new XY(0d, 1000d));
		e4.setSize(new XY(50d, 200d));

		Element<Diagram, ElementDetail> e5 = new Element<Diagram, ElementDetail>("_class", diag, "BottomMiddle");
		e5.setPosition(new XY(500d, 1100d));
		e5.setSize(new XY(50d, 50d));

		Element<Diagram, ElementDetail> e6 = new Element<Diagram, ElementDetail>("_class", diag, "MiddleLeft");
		e6.setPosition(new XY(25d, 500d));
		e6.setSize(new XY(10d, 10d));

		Element<Diagram, ElementDetail> e7 = new Element<Diagram, ElementDetail>("_class", diag, "MiddleRight");
		e7.setPosition(new XY(975d, 500d));
		e7.setSize(new XY(10d, 10d));

		Connector<Diagram, Element> c1_3 = new Connector<Diagram, Element>("_extends", diag, "c1_3");
		c1_3.setStartElement(e1);
		c1_3.setEndElement(e3);

		Connector<Diagram, Element> c2_4 = new Connector<Diagram, Element>("_extends", diag, "c2_4");
		c2_4.setStartElement(e2);
		c2_4.setEndElement(e4);

		Connector<Diagram, Element> c3_7 = new Connector<Diagram, Element>("_extends", diag, "c3_7");
		c3_7.setStartElement(e3);
		c3_7.setEndElement(e7);

		Connector<Diagram, Element> c4_6 = new Connector<Diagram, Element>("_extends", diag, "c4_6");
		c4_6.setStartElement(e4);
		c4_6.setEndElement(e6);

	}

}
