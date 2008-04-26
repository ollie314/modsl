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

import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.modsl.core.agt.model.Node;
import org.modsl.core.agt.visitor.LayoutVisitor;
import org.modsl.core.agt.visitor.StringTemplateVisitor;

public class BasicFlowTest extends AbstractBasicTest {

	protected static final String ROOTDIR = "cfg";
	protected static final String CFGDIR = ROOTDIR + "/basic";
	protected static final String NAME = "basic";

	@Test
	public void flow() throws RecognitionException {

		Node<BasicMetaType> root = parse("graph g { n1->n2; n1->n3->n4->n5; n2->n3; }");

		BasicLayoutGroupLoader lcgl = new BasicLayoutGroupLoader(CFGDIR, BasicMetaType.class);
		lcgl.load();

		LayoutVisitor<BasicMetaType> fmv = new LayoutVisitor<BasicMetaType>(CFGDIR, NAME, BasicMetaType.class);
		root.accept(fmv);

		StringTemplateVisitor<BasicMetaType> stv = new StringTemplateVisitor<BasicMetaType>(ROOTDIR + ":" + CFGDIR, NAME, 0);
		root.accept(stv);
		String result = stv.toString();

		log.debug(result);

	}

}
