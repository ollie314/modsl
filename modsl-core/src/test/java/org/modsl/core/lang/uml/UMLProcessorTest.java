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
import org.modsl.core.Utils;
import org.modsl.core.agt.model.Pt;
import org.modsl.core.lang.SVGCollector;

public class UMLProcessorTest extends AbstractUMLTest {

	protected static SVGCollector svgCollector = new SVGCollector("etc/svg-out", "uml");

	@Test
	public void process1() throws Exception {
		process("collab coll1 { c1:o1->c2:o2.m2()->c3:o3.m3()->c1:o1.m4(); }", new Pt(400, 320));
	}

	@Test
	public void process2() throws RecognitionException, IOException {
		process(
				"collab coll2 { :o1->:o2.m2(); :o1->:o3.m3(); :o2->:o4.m4(); :o2->:o5.m5(); :o3->:o6.m6(); :o3->:o7.m7(); :o6->:o7.m8(); :o1->:o6.m9(); }",
				new Pt(400, 320));
	}

	@Test
	public void process3() throws RecognitionException, IOException {
		process(Utils.fromFile("samples/uml/collab3.modsl"), new Pt(640, 480));
	}

	private void process(String s, Pt reqSize) throws RecognitionException, IOException {
		String result = processor.process(s, reqSize);
		svgCollector.collect(processor.getRoot().getName(), result, reqSize);
	}

}
