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

package org.modsl.core.lang.basic;

import java.io.IOException;

import org.antlr.runtime.RecognitionException;
import org.apache.log4j.Logger;
import org.modsl.core.agt.model.Pt;
import org.modsl.core.lang.SVGCollector;

public abstract class AbstractBasicTest {

	static SVGCollector svgCollector = new SVGCollector("etc/svg-out", "basic");

	final Logger log = Logger.getLogger(getClass());

	BasicProcessor processor;

	public AbstractBasicTest() {
		processor = new BasicProcessor();
		processor.init();
	}

	void process(String s, Pt reqSize) throws RecognitionException, IOException {
		String result = processor.process(s, reqSize);
		svgCollector.collect(processor.getGraph().getName(), result, reqSize);
	}

}