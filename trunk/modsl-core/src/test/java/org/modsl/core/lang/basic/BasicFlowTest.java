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

import static junit.framework.Assert.assertTrue;

import java.io.IOException;

import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.modsl.core.agt.model.Node;
import org.modsl.core.agt.model.Pt;
import org.modsl.core.agt.visitor.StringTemplateVisitor;
import org.modsl.core.agt.visitor.layout.LayoutVisitor;
import org.modsl.utils.Utils;

public class BasicFlowTest extends AbstractBasicTest {

	protected static final String PATH = "cfg/basic:cfg";
	protected static final String NAME = "basic";

	protected BasicConfigLoader cfgLoader = new BasicConfigLoader(PATH, NAME, BasicMetaType.class);
	protected LayoutVisitor<BasicMetaType> layoutVisitor = new LayoutVisitor<BasicMetaType>();
	protected StringTemplateVisitor<BasicMetaType> stVisitor = new StringTemplateVisitor<BasicMetaType>(PATH, NAME, 0);

	public BasicFlowTest() {
		cfgLoader.load();
	}

	@Test
	public void flow1() throws Exception {

		Node<BasicMetaType> root = parse("graph g1 { n1->n2; n1->n3->n4->n5; n2->n3; }");
		root.setReqSize(new Pt(640, 480));
		
		root.accept(layoutVisitor);

		assertSizes(root);

		root.rescale(root.getReqSize());

		root.accept(stVisitor);
		String result = stVisitor.toString();

		// log.debug(result);

		Utils.toFile("etc/svg-out/" + NAME + "_" + root.getName() + ".svg", result);

	}

	@Test
	public void flow2() throws RecognitionException, IOException {
		Node<BasicMetaType> root = parse("graph g2 { n1->n2->n3->n4->n5; }");
		root.setReqSize(new Pt(640, 480));
		root.accept(layoutVisitor);
		root.rescale(root.getReqSize());
		root.accept(stVisitor);
		Utils.toFile("etc/svg-out/" + NAME + "_" + root.getName() + ".svg", stVisitor.toString());
	}

	private void assertSizes(Node<BasicMetaType> root) {
		Pt s = root.getNode("n1").getSize();
		assertTrue(s.x > 20 && s.x < 30);
		assertTrue(s.y > 16 && s.y < 20);
	}

}
