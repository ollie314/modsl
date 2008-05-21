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

public class UMLClassProcessorTest extends AbstractUMLTest {

	protected static SVGCollector svgCollector = new SVGCollector("etc/svg-out", "uml_class");

	@Test
	public void process0() throws Exception {
		process("class diagram c0 { }");
	}

	@Test
	public void process1c() throws Exception {
		process("class diagram c1c { class class1 { } }");
	}

	@Test
	public void process1cv() throws Exception {
		process("class diagram c1cv { class class1 { var1; } }");
	}

	@Test
	public void process1cm() throws Exception {
		process("class diagram c1cm { class class1 { method1(); } }");
	}

	@Test
	public void process1cvm() throws Exception {
		process("class diagram c1cvm { class class1 { v1; m1(); v2; m2(); v3; m3(); } }");
	}

	@Test
	public void process1cvmss() throws Exception {
		process("class diagram c1cvmss { class class1 { var1; static var2; method1(); static method2(); } }");
	}

	@Test
	public void process1i() throws Exception {
		process("class c1i { interface interface1 { } }");
	}

	@Test
	public void process1mp() throws Exception {
		process("class diagram c1mp { class class1 { m1(p1,     p2, p3); } }");
	}


    @Test
    public void process2() throws Exception {
        process("class diagram c2 { class c1 implements i1 { v1; m1(p1); } interface i1 { m1(p1); } }");
    }

	
	private void process(String s) throws RecognitionException, IOException {
		String result = processor.process(s);
		svgCollector.collect(processor.getGraph().getName(), result, processor.getGraph().getSize());
	}

}
