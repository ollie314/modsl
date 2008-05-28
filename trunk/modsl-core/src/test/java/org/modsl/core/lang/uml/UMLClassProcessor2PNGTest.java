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

package org.modsl.core.lang.uml;

import java.io.IOException;

import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.modsl.core.lang.SVGCollector;
import org.modsl.core.utils.Utils;

public class UMLClassProcessor2PNGTest extends AbstractUMLTest {

    protected static SVGCollector svgCollector = new SVGCollector("etc/svg-out", "uml_class2");

    @Test
    public void process2g() throws Exception {
        process("class diagram c2g (width:200, height:400) { class c1 extends c2 implements i1, i2 { v1:String; m1(p1):Date; m2(p2):Date; } "
                + " interface i1 { m1(p1):List; } interface i2 { m2(p2); } class c2 {} }");
    }

    @Test
    public void process2a() throws Exception {
        process("class diagram c2a (width:240, height:400) { class c1 { v1; m1(p1); m2(p2); 1->*(c2); 0..1->n..m(c3); } "
                + " class c2 { m1(p1); 1->1(c3); } class c3 extends i1 { m2(p2); 1..*->1(c4); }  class c4 {} interface i1{} } ");
    }

    @Test
    public void process2b() throws Exception {
        process("class diagram c2b { class AbstractBox { size; pos; getSize(); getPos(); } class AbstractLabel extends AbstractBox { name; getTextPos(); } "
                + "class NodeLabel extends AbstractLabel { offset; getOffset(); } class GraphLabel extends AbstractLabel {}"
                + " class EdgeLabel extends AbstractLabel { anchor1; anchor2; offset; getAnchor1(); getAnchor2(); getOffset(); } } ");
    }

    @Test
    public void process3() throws RecognitionException, IOException {
        process(Utils.fromFile("samples/uml/class_self.modsl"));
    }

    @Test
    public void process3noagg() throws RecognitionException, IOException {
        process(Utils.fromFile("samples/uml/class_self_noagg.modsl"));
    }

    private void process(String s) throws RecognitionException, IOException {
        String result = processor.process(s);
        svgCollector.collect(processor.getGraph().getName(), result, processor.getGraph().getSize());
    }

}
