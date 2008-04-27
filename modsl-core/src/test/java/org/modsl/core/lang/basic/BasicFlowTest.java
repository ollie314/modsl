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

import org.junit.Test;
import org.modsl.core.agt.model.Node;
import org.modsl.core.agt.model.Pt;
import org.modsl.core.agt.visitor.LayoutVisitor;
import org.modsl.core.agt.visitor.StringTemplateVisitor;
import org.modsl.utils.Utils;

public class BasicFlowTest extends AbstractBasicTest {

	protected static final String PATH = "cfg/basic:cfg";
	protected static final String NAME = "basic";

	@Test
	public void flow() throws Exception {

		Node<BasicMetaType> root = parse("graph g { n1->n20; n1->n300->n4000->n50000; n20->n300; }");
		root.getReqSize().x = 640;
		root.getReqSize().y = 480;

		BasicConfigLoader cfgLoader = new BasicConfigLoader(PATH, NAME, BasicMetaType.class);
		cfgLoader.load();

		LayoutVisitor<BasicMetaType> lv = new LayoutVisitor<BasicMetaType>();
		root.accept(lv);

		assertSizes(root);

		root.rescale(root.getReqSize());

		StringTemplateVisitor<BasicMetaType> stv = new StringTemplateVisitor<BasicMetaType>(PATH, NAME, 0);
		root.accept(stv);
		String result = stv.toString();

		log.debug(result);

		Utils.toFile("etc/svg-out/" + NAME + "_" + root.getName() + ".svg", result);

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
