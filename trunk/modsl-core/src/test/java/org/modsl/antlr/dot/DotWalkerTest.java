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

import static org.junit.Assert.assertTrue;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.antlr.stringtemplate.CommonGroupLoader;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.modsl.antlr.dot.DotWalker.dotGraph_return;
import org.modsl.st.STErrorListener;

public class DotWalkerTest extends AbstractDotTest {

    protected final Logger log = Logger.getLogger(getClass());

    protected static final String ROOT = "st";
    protected static final String STGDIRS = ROOT + ":" + ROOT + "/dot";

    protected StringTemplateGroup group;

    public DotWalkerTest() {
        StringTemplateGroup.registerGroupLoader(new CommonGroupLoader(STGDIRS, new STErrorListener()));
        group = StringTemplateGroup.loadGroup("dot");
        group.setRefreshInterval(0);
    }

    @Test
    public void node2() throws RecognitionException {
        TokensNTree tnt = parse("graph g { n0 [a0=5]; n1->n2->n3; }");
        CommonTreeNodeStream nodes = new CommonTreeNodeStream(tnt.tree);
        nodes.setTokenStream(tnt.tokens);
        DotWalker walker = new DotWalker(nodes);
        walker.setTemplateLib(group);
        dotGraph_return ret = walker.dotGraph();
        log.debug("\n" + ret.toString());
        assertTrue(true);
    }

}
