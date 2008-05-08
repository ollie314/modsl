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

import org.apache.log4j.Logger;
import org.junit.Before;
import org.modsl.core.agt.TMetaType;

public abstract class AbstractAGTModelTest {

    protected Logger log = Logger.getLogger(getClass());

    protected Graph root = new Graph(TMetaType.GRAPH);
    protected Node n1 = new Node(TMetaType.NODE, "n1");
    protected Node n2 = new Node(TMetaType.NODE, "n2");
    protected Node n3 = new Node(TMetaType.NODE, "n3");
    protected Node n4 = new Node(TMetaType.NODE, "n4");
    protected Node n5 = new Node(TMetaType.NODE, "n5");
    protected Node n6 = new Node(TMetaType.NODE, "n6");
    protected Node n7 = new Node(TMetaType.NODE, "n7");
    protected Edge e1_3 = new Edge(TMetaType.EDGE, "e1_3", n1, n3);
    protected Edge e2_4 = new Edge(TMetaType.EDGE, "e2_4", n2, n4);
    protected Edge e3_7 = new Edge(TMetaType.EDGE, "e3_7", n3, n7);
    protected Edge e4_6 = new Edge(TMetaType.EDGE, "e4_6", n4, n6);
    protected Edge e1_5 = new Edge(TMetaType.EDGE, "e1_5", n1, n5);
    protected Edge e2_5 = new Edge(TMetaType.EDGE, "e2_5", n2, n5);
    protected Edge e6_2 = new Edge(TMetaType.EDGE, "e6_2", n6, n2); // cycle
    protected Edge e7_5 = new Edge(TMetaType.EDGE, "e7_5", n7, n5);

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
