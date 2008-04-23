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

import org.antlr.runtime.RecognitionException;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.modsl.agt.Node;

public class DotTest extends AbstractDotTest {

    protected final Logger log = Logger.getLogger(getClass());

    @Test
    public void root() throws RecognitionException {
        Node root = parse("graph g {}");
        assertEquals("g", root.getName());
        assertEquals(null, root.getParent());
        assertEquals(0, root.getChildren().size());
        //parse("graph g { n1 [a=1,label=\"hello\"]; stmt2; }");
        //parse("graph g { n0; n1->n2; n3->n4->n5; n6->n7[a=5]; }");
    }

    @Test
    public void basic() throws RecognitionException {
        //Node root = parse("graph g {\n stmt1; \n \"stmt2\"; 12345; \n }");
        //log.debug(root);
        //parse("graph g { n1 [a=1,label=\"hello\"]; stmt2; }");
        //parse("graph g { n0; n1->n2; n3->n4->n5; n6->n7[a=5]; }");
    }

}
