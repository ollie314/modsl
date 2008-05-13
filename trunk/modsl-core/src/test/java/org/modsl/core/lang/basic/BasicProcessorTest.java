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

package org.modsl.core.lang.basic;

import java.io.IOException;

import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.modsl.core.agt.model.Pt;
import org.modsl.core.lang.SVGCollector;

public class BasicProcessorTest extends AbstractBasicTest {

	@Test
	public void process1() throws Exception {
		process("graph g1 { n1->n2; n1->n3->n4->n5; n2->n3; n5->n8->n9; n9->n4; n8-> n4; }", new Pt(400, 320));
	}

	@Test
	public void process2() throws RecognitionException, IOException {
		process("graph g2 { n1->n2->n3->n4->n5->n6->n7->n8; }", new Pt(400, 320));
	}

	@Test
	public void process3() throws RecognitionException, IOException {
		process("graph g3 { n1->n2->n3->n4->n5->n6->n7->n8; n5->n1->n3; n2->n4->n6->n8->n5->n2; "
				+ "n3->n5->n7; n6->n1->n4->n8; n6->n2->n8->n1->n7; n4->n7->n2; n8->n3->n6; n3->n7; }", new Pt(400, 320));
	}

	@Test
	public void process4() throws RecognitionException, IOException {
		process("graph g4 { n1->n2; n1->n3; n2->n4; n2->n5; n3->n6; n3->n7; n6->n7; n1->n6; }", new Pt(500, 420));
	}

}
