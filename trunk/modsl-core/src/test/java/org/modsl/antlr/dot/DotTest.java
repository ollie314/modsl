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

package org.modsl.antlr.dot;

import static org.junit.Assert.assertEquals;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.apache.log4j.Logger;
import org.junit.Test;

public class DotTest {

	protected final Logger log = Logger.getLogger(getClass());

	private CommonTree parse(String s) throws RecognitionException {
		ANTLRStringStream input = new ANTLRStringStream(s);
		DotLexer lexer = new DotLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		DotParser parser = new DotParser(tokens);
		DotParser.dotGraph_return r = parser.dotGraph();
		return (CommonTree) r.getTree();
	}

	@Test
	public void node() throws RecognitionException {
		CommonTree t = parse("graph g {\n stmt1; \n \"stmt2\"; 12345; \n }");
		assertEquals("g", t.token.getText());
		assertEquals("stmt1", t.getChild(0).getText());
		assertEquals("\"stmt2\"", t.getChild(1).getText());
		assertEquals("12345", t.getChild(2).getText());
	}

	@Test
	public void attributeList() throws RecognitionException {
		CommonTree t = parse("graph g { n1 [a=1,label=\"hello\"]; stmt2; }");
		CommonTree n1 = (CommonTree) t.getChild(0);
		CommonTree attrList = (CommonTree) n1.getChild(0);
		assertEquals("ATTRIBUTE", attrList.getChild(0).getText());
		assertEquals("a", attrList.getChild(0).getChild(0).getText());
		assertEquals("1", attrList.getChild(0).getChild(1).getText());
		assertEquals("ATTRIBUTE", attrList.getChild(1).getText());
		assertEquals("label", attrList.getChild(1).getChild(0).getText());
		assertEquals("\"hello\"", attrList.getChild(1).getChild(1).getText());
	}

	@Test
	public void edge() throws RecognitionException {
		CommonTree t = parse("graph g { n0; n1->n2; n3->n4->n5; n6->n7[a=5]; }");
		log.debug(t.toStringTree());
	}

}
