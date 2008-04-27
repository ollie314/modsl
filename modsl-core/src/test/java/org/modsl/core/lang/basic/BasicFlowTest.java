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

import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.modsl.core.agt.model.Node;
import org.modsl.core.agt.model.Pt;
import org.modsl.core.agt.visitor.LayoutVisitor;
import org.modsl.core.agt.visitor.StringTemplateVisitor;

public class BasicFlowTest extends AbstractBasicTest {

	protected static final String ROOTDIR = "cfg";
	protected static final String CFGDIR = ROOTDIR + "/basic";
	protected static final String NAME = "basic";

	@Test
	public void flow() throws RecognitionException {

		Node<BasicMetaType> root = parse("graph g { n1->n20; n1->n300->n4000->n50000; n20->n300; }");
		root.getSize().x = 640;
		root.getSize().y = 480;

		BasicConfigLoader cfgLoader = new BasicConfigLoader(CFGDIR, NAME, BasicMetaType.class);
		cfgLoader.load();

		LayoutVisitor<BasicMetaType> lv = new LayoutVisitor<BasicMetaType>();
		root.accept(lv);

		assertSizes(root);

		StringTemplateVisitor<BasicMetaType> stv = new StringTemplateVisitor<BasicMetaType>(ROOTDIR + ":" + CFGDIR, NAME, 0);
		root.accept(stv);
		String result = stv.toString();

		log.debug(result);

	}

	private void assertSizes(Node<BasicMetaType> root) {

		Pt s = root.getNode("n1").getSize();
		assertTrue(s.x > 20 && s.x < 30);
		assertTrue(s.y > 16 && s.y < 20);

		s = root.getNode("n50000").getSize();
		assertTrue(s.x > 50 && s.x < 55);
		assertTrue(s.y > 16 && s.y < 20);

	}

}
