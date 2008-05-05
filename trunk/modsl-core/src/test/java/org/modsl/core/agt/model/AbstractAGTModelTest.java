/**
 * Copyright 2008 Andrew Vishnyakov <avishn@gmail.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.modsl.core.agt.model;

import org.junit.Before;
import org.modsl.core.agt.TMetaType;

public abstract class AbstractAGTModelTest {

    protected Node<TMetaType> root = new Node<TMetaType>(TMetaType.GRAPH);
    protected Node<TMetaType> n1 = new Node<TMetaType>(TMetaType.NODE, "TopLeft");
    protected Node<TMetaType> n2 = new Node<TMetaType>(TMetaType.NODE, "TopRight");
    protected Node<TMetaType> n3 = new Node<TMetaType>(TMetaType.NODE, "BottomRight");
    protected Node<TMetaType> n4 = new Node<TMetaType>(TMetaType.NODE, "BottomLeft");
    protected Node<TMetaType> n5 = new Node<TMetaType>(TMetaType.NODE, "BottomMiddle");
    protected Node<TMetaType> n6 = new Node<TMetaType>(TMetaType.NODE, "MiddleLeft");
    protected Node<TMetaType> n7 = new Node<TMetaType>(TMetaType.NODE, "MiddleRight");
    protected Edge<TMetaType> e1_3 = new Edge<TMetaType>(TMetaType.EDGE, "e1_3", n1, n3);
    protected Edge<TMetaType> e2_4 = new Edge<TMetaType>(TMetaType.EDGE, "e2_4", n2, n4);
    protected Edge<TMetaType> e3_7 = new Edge<TMetaType>(TMetaType.EDGE, "e3_7", n3, n7);
    protected Edge<TMetaType> e4_6 = new Edge<TMetaType>(TMetaType.EDGE, "e4_6", n4, n7);
    protected Edge<TMetaType> e1_5 = new Edge<TMetaType>(TMetaType.EDGE, "e1_5", n1, n5);
    protected Edge<TMetaType> e2_5 = new Edge<TMetaType>(TMetaType.EDGE, "e2_5", n2, n5);
    protected Edge<TMetaType> e6_2 = new Edge<TMetaType>(TMetaType.EDGE, "e6_2", n6, n2); // cycle
    protected Edge<TMetaType> e7_5 = new Edge<TMetaType>(TMetaType.EDGE, "e7_5", n7, n5);

    @Before
    public void setUp() throws Exception {

        root.add(n1);
        n1.setPos(new Pt(0d, 0d));
        n1.setSize(new Pt(10d, 10d));

        root.add(n2);
        n2.setPos(new Pt(1000d, 0d));
        n2.setSize(new Pt(200d, 50d));

        root.add(n3);
        n3.setPos(new Pt(1000d, 1000d));
        n3.setSize(new Pt(10d, 10d));

        root.add(n4);
        n4.setPos(new Pt(0d, 1000d));
        n4.setSize(new Pt(50d, 200d));

        root.add(n5);
        n5.setPos(new Pt(500d, 1100d));
        n5.setSize(new Pt(50d, 50d));

        root.add(n6);
        n6.setPos(new Pt(25d, 500d));
        n6.setSize(new Pt(10d, 10d));

        root.add(n7);
        n7.setPos(new Pt(975d, 500d));
        n7.setSize(new Pt(10d, 10d));

        root.add(e1_3);
        root.add(e2_4);
        root.add(e3_7);
        root.add(e4_6);
        root.add(e1_5);
        root.add(e2_5);
        root.add(e6_2);
        root.add(e7_5);

    }

}
