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
import static org.junit.Assert.assertTrue;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.apache.log4j.Logger;
import org.junit.Test;

public class DotTest {

    protected final Logger log = Logger.getLogger(getClass());

    private CommonTree parse(String s) throws RecognitionException {
        ANTLRStringStream input = new ANTLRStringStream("\n\r\t");
        DotLexer lexer = new DotLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        DotParser parser = new DotParser(tokens);
        DotParser.emptyRule_return r = parser.emptyRule();
        return (CommonTree) r.getTree();
    }

    @Test
    public void empty() throws RecognitionException {
        CommonTree t = parse("");
        assertTrue(t.isNil());
        assertEquals(0, t.getChildCount());
    }

    @Test
    public void ws() throws RecognitionException {
        CommonTree t = parse("        \n \t\r\n \n\r");
        assertEquals(0, t.getChildCount());
    }
    
    @Test
    public void newline() throws RecognitionException {
        CommonTree t = parse("\n\r\n\n\r");
        assertEquals(0, t.getChildCount());
    }

    @Test
    public void garbage() throws RecognitionException {
        CommonTree t = parse("\n\rasd d\n\n\r");
        assertEquals(0, t.getChildCount());
    }

}
