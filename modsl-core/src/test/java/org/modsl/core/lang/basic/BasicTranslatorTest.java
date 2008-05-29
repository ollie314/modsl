package org.modsl.core.lang.basic;

import java.io.IOException;

import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.modsl.core.AbstractModSLTest;
import org.modsl.core.ImageCollector;
import org.modsl.core.render.StyleLoader;

public class BasicTranslatorTest extends AbstractModSLTest {

    static ImageCollector imageCollector = new ImageCollector("etc/png-out", "basic");
    static BasicTranslator translator = new BasicTranslator();

    static {
        StyleLoader stl = new StyleLoader();
        stl.load("cfg/basic:cfg", "basic", BasicMetaType.class);
    }

    @Test
    public void test() throws RecognitionException, IOException {
        imageCollector.collect("g1", translator
                .translate("graph g1 { n1->n2; n1->n3->n4->n5; n2->n3; n5->n8->n9; n9->n4; n8-> n4; }"), "png");
    }

}
