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
import org.apache.log4j.Logger;
import org.junit.Test;
import org.modsl.core.agt.Node;
import org.modsl.core.agt.dot.DotType;

public class DotGrammarTest {

    protected final Logger log = Logger.getLogger(getClass());

    protected Node<DotType> parse(String s) throws RecognitionException {
        ANTLRStringStream input = new ANTLRStringStream(s);
        DotLexer lexer = new DotLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        DotParser parser = new DotParser(tokens);
        parser.dotGraph();
        return parser.root;
    }

    @Test
    public void root() throws RecognitionException {
        Node<DotType> root = parse("graph g {}");
        assertEquals("g", root.getName());
        assertEquals(null, root.getParent());
        assertEquals(0, root.getNodes().size());
    }

    @Test
    public void nodes() throws RecognitionException {
        Node<DotType> root = parse("graph g {\n stmt1; \n \"stmt2\"; 12345; \n }");
        assertEquals(3, root.getNodes().size());
        assertEquals("stmt1", root.getNodes().get(0).getName());
        assertEquals("stmt1", root.getNode("stmt1").getName());
        assertEquals("\"stmt2\"", root.getNodes().get(1).getName());
        assertEquals("\"stmt2\"", root.getNode("\"stmt2\"").getName());
        assertEquals("12345", root.getNodes().get(2).getName());
        assertEquals("12345", root.getNode("12345").getName());
    }

    @Test
    public void edges() throws RecognitionException {
        Node<DotType> root = parse("graph g { n0; n1->n2; n3->n4->n5; n6->n7; }");
        assertEquals(4, root.getEdges().size());
    }

}
