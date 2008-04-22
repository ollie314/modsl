package org.modsl.st.dot;

import static org.junit.Assert.assertTrue;

import org.antlr.stringtemplate.CommonGroupLoader;
import org.antlr.stringtemplate.StringTemplateErrorListener;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.apache.log4j.Logger;
import org.junit.Test;

public class DotSTTest {

    protected static class STErrorListener implements StringTemplateErrorListener {

        @Override
        public void error(String m, Throwable t) {
            log.error(m, t);
        }

        @Override
        public void warning(String m) {
            log.warn(m);
        }

    }

    private static Logger log = Logger.getLogger(DotSTTest.class);

    protected static final String ROOT = "/st";
    protected static final String STGDIRS = ROOT + ":" + ROOT + "/dot";

    protected StringTemplateGroup group;

    public DotSTTest() {
        StringTemplateGroup.registerGroupLoader(new CommonGroupLoader(STGDIRS, new STErrorListener()));
        this.group = StringTemplateGroup.loadGroup("dot");
    }

    @Test
    public void sample() {
        assertTrue(true);
    }

}
