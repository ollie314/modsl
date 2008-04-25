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

package org.modsl.test.st.demo;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.antlr.stringtemplate.CommonGroupLoader;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.modsl.st.STErrorListener;

public class DemoSTTest {

    protected Logger log = Logger.getLogger(getClass());

    protected static final String ROOT = "test/st/demo";
    protected static final String STGDIRS = ROOT + ":" + ROOT + "/sub";

    protected StringTemplateGroup group;

    public DemoSTTest() {
        StringTemplateGroup.registerGroupLoader(new CommonGroupLoader(STGDIRS, new STErrorListener()));
        group = StringTemplateGroup.loadGroup("sub");
        group.setRefreshInterval(0);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void keyValue() {
        StringTemplate st = group.getInstanceOf("keyValue");
        Map attrs = new HashMap();
        attrs.put("key", "k1");
        attrs.put("value", "v1");
        st.setAttributes(attrs);
        assertEquals("k1.v1", st.toString());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void starry() {
        StringTemplate st = group.getInstanceOf("starry");
        Map attrs = new HashMap();
        attrs.put("string", "abc");
        st.setAttributes(attrs);
        assertEquals("***abc***", st.toString());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void unique() {
        StringTemplate st = group.getInstanceOf("unique");
        Map attrs = new HashMap();
        attrs.put("string", "abc");
        st.setAttributes(attrs);
        assertEquals("abc_unique", st.toString());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void regionOuter() {
        StringTemplate st = group.getInstanceOf("regionOuter");
        Map attrs = new HashMap();
        attrs.put("string", "abc");
        st.setAttributes(attrs);
        assertEquals("###///abc///###", st.toString());
    }

}
