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
		DotASTLexer lexer = new DotASTLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		DotASTParser parser = new DotASTParser(tokens);
		DotASTParser.dotGraph_return r = parser.dotGraph();
		return (CommonTree) r.getTree();
	}

	@Test
	public void node() throws RecognitionException {
		CommonTree t = parse("graph g {\n stmt1; \n \"stmt2\"; 12345; \n }");
		assertEquals("g", t.token.getText());
		assertEquals("stmt1", t.getChild(0).getChild(0).getText());
		assertEquals("\"stmt2\"", t.getChild(1).getChild(0).getText());
		assertEquals("12345", t.getChild(2).getChild(0).getText());
	}

	@Test
	public void attributeList() throws RecognitionException {
		CommonTree t = parse("graph g { n1 [a=1,label=\"hello\"]; stmt2; }");
		// log.debug(t.toStringTree());
		CommonTree n1 = (CommonTree) t.getChild(0);
		assertEquals("n1", n1.getChild(0).getText());
		assertEquals("ATTRIBUTE", n1.getChild(1).getText());
		assertEquals("a", n1.getChild(1).getChild(0).getText());
		assertEquals("1", n1.getChild(1).getChild(1).getText());
		assertEquals("ATTRIBUTE", n1.getChild(2).getText());
		assertEquals("label", n1.getChild(2).getChild(0).getText());
		assertEquals("\"hello\"", n1.getChild(2).getChild(1).getText());
	}

	@Test
	public void edge() throws RecognitionException {
		CommonTree t = parse("graph g { n0; n1->n2; n3->n4->n5; n6->n7[a=5]; }");
		// log.debug(t.toStringTree());
        assertEquals("NODE", t.getChild(0).getText());
        assertEquals("n0", t.getChild(0).getChild(0).getText());
        assertEquals("EDGE", t.getChild(1).getText());
        assertEquals("n1", t.getChild(1).getChild(0).getText());
        assertEquals("n2", t.getChild(1).getChild(1).getText());
        assertEquals("EDGE", t.getChild(2).getText());
        assertEquals("n3", t.getChild(2).getChild(0).getText());
        assertEquals("n4", t.getChild(2).getChild(1).getText());
        assertEquals("n5", t.getChild(2).getChild(2).getText());
        assertEquals("EDGE", t.getChild(3).getText());
        assertEquals("n6", t.getChild(3).getChild(0).getText());
        assertEquals("n7", t.getChild(3).getChild(1).getText());
        assertEquals("ATTRIBUTE", t.getChild(3).getChild(2).getText());
        assertEquals("a", t.getChild(3).getChild(2).getChild(0).getText());
        assertEquals("5", t.getChild(3).getChild(2).getChild(1).getText());
	}

}
