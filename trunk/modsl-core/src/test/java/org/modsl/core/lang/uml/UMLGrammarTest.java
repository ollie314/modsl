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

package org.modsl.core.lang.uml;

import static org.junit.Assert.assertEquals;

import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.modsl.core.agt.model.Node;
import org.modsl.core.agt.visitor.ToStringVisitor;

public class UMLGrammarTest extends AbstractUMLTest {

	@Test
	public void root() throws RecognitionException {
		Node<UMLMetaType> root = processor.parse("collab gr {}");
		assertEquals("gr", root.getName());
		assertEquals(null, root.getParent());
		assertEquals(0, root.getNodes().size());
	}

	@Test
	public void edges() throws RecognitionException {
		// Node<UMLMetaType> root = processor.parse("collab ge { n1->n2.m1();
		// n2->n3.m2()->n4; }");
		Node<UMLMetaType> root = processor.parse("collab ge { n1->:n2.m2; n3:n4->n5.m5->:n6.m6(); }");
//		log.debug(new ToStringVisitor<UMLMetaType>().toString(root));
		//assertEquals(3, root.getNodes().size());
		//assertEquals(0, root.getEdges().size());
	}

}
