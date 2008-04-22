package org.modsl.st.dot;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.antlr.stringtemplate.CommonGroupLoader;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.modsl.st.STErrorListener;

public class DotSTTest {

    protected Logger log = Logger.getLogger(getClass());

    protected static final String ROOT = "st_test";
    protected static final String STGDIRS = ROOT + ":" + ROOT + "/dot";

    protected StringTemplateGroup group;

    public DotSTTest() {
        StringTemplateGroup.registerGroupLoader(new CommonGroupLoader(STGDIRS, new STErrorListener()));
        group = StringTemplateGroup.loadGroup("dot_test");
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
