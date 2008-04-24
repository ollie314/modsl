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

package org.modsl.test.st.visitor;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.antlr.stringtemplate.CommonGroupLoader;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.modsl.st.STErrorListener;

public class VisitorSTTest {

    protected Logger log = Logger.getLogger(getClass());

    protected static final String ROOT = "test/st/visitor";
    protected static final String STGDIRS = ROOT;

    protected StringTemplateGroup group;

    public VisitorSTTest() {
        StringTemplateGroup.registerGroupLoader(new CommonGroupLoader(STGDIRS, new STErrorListener()));
        group = StringTemplateGroup.loadGroup("visitor_demo", DefaultTemplateLexer.class, null);
        group.setRefreshInterval(0);
    }

    @Test
    public void graph() {
        StringTemplate st = group.getInstanceOf("GRAPH_in");
       /* Map attrs = new HashMap();
        attrs.put("key", "k1");
        attrs.put("value", "v1");
        st.setAttributes(attrs);
        assertEquals("k1.v1", st.toString());*/
    }

}
