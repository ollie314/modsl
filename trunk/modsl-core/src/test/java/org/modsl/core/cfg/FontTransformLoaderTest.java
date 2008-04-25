package org.modsl.core.cfg;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.modsl.core.agt.model.MetaType;

public class FontTransformLoaderTest {

    protected Logger log = Logger.getLogger(getClass());

    @Test
    public void load() {
        FontTransformLoader ftl = new FontTransformLoader("test/props/ft", "dot", FType.class);
        ftl.load();
        log.debug(ftl);
    }

    public static enum FType implements MetaType {

        GRAPH, NODE, EDGE;

        public FontTransform fontTransform;

        public FontTransform getFontTransform() {
            return fontTransform;
        }

        public void setFontTransform(FontTransform fontTransform) {
            this.fontTransform = fontTransform;
        }

    }

}
