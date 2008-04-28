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

import org.junit.Before;
import org.modsl.core.agt.TMetaType;

public abstract class AbstractAGTModelTest {

	Node<TMetaType> root;

	@Before
	public void setUp() throws Exception {

		root = new Node<TMetaType>(TMetaType.GRAPH);

		Node<TMetaType> n1 = new Node<TMetaType>(TMetaType.NODE, "TopLeft");
		root.add(n1);
		n1.setPos(new Pt(0d, 0d));
		n1.setSize(new Pt(10d, 10d));

		Node<TMetaType> n2 = new Node<TMetaType>(TMetaType.NODE, "TopRight");
		root.add(n2);
		n2.setPos(new Pt(1000d, 0d));
		n2.setSize(new Pt(200d, 50d));

		Node<TMetaType> n3 = new Node<TMetaType>(TMetaType.NODE, "BottomRight");
		root.add(n3);
		n3.setPos(new Pt(1000d, 1000d));
		n3.setSize(new Pt(10d, 10d));

		Node<TMetaType> n4 = new Node<TMetaType>(TMetaType.NODE, "BottomLeft");
		root.add(n4);
		n4.setPos(new Pt(0d, 1000d));
		n4.setSize(new Pt(50d, 200d));

		Node<TMetaType> n5 = new Node<TMetaType>(TMetaType.NODE, "BottomMiddle");
		root.add(n5);
		n5.setPos(new Pt(500d, 1100d));
		n5.setSize(new Pt(50d, 50d));

		Node<TMetaType> n6 = new Node<TMetaType>(TMetaType.NODE, "MiddleLeft");
		root.add(n6);
		n6.setPos(new Pt(25d, 500d));
		n6.setSize(new Pt(10d, 10d));

		Node<TMetaType> n7 = new Node<TMetaType>(TMetaType.NODE, "MiddleRight");
		root.add(n7);
		n7.setPos(new Pt(975d, 500d));
		n7.setSize(new Pt(10d, 10d));

		Edge<TMetaType> e1_3 = new Edge<TMetaType>(TMetaType.EDGE, "e1_3", n1, n3);
		root.add(e1_3);

		Edge<TMetaType> e2_4 = new Edge<TMetaType>(TMetaType.EDGE, "e2_4", n2, n4);
		root.add(e2_4);

		Edge<TMetaType> e3_7 = new Edge<TMetaType>(TMetaType.EDGE, "e3_7", n3, n7);
		root.add(e3_7);

		Edge<TMetaType> e4_6 = new Edge<TMetaType>(TMetaType.EDGE, "e4_6", n4, n7);
		root.add(e4_6);

	}

}
