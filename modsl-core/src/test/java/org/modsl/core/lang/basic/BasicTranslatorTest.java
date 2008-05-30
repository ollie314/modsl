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
    public void p1() throws RecognitionException, IOException {
        imageCollector.collect("g1", translator
                .translate("graph g1 (width:400, height:320) { n1->n2; n1->n3->n4->n5; n2->n3; n5->n8->n9; n9->n4; n8-> n4; }"), "png");
    }

    @Test
    public void p2() throws RecognitionException, IOException {
        imageCollector.collect("g2", translator.translate("graph g2 { n1->n2->n3->n4->n5->n6->n7->n8; }"), "png");
    }

    @Test
    public void p3() throws RecognitionException, IOException {
        imageCollector.collect("g3", translator
                .translate("graph g3 { n1->n2->n3->n4->n5->n6->n7->n8; n5->n1->n3; n2->n4->n6->n8->n5->n2; "
                        + "n3->n5->n7; n6->n1->n4->n8; n6->n2->n8->n1->n7; n4->n7->n2; n8->n3->n6; n3->n7; }"), "png");
    }

    @Test
    public void p4() throws RecognitionException, IOException {
        imageCollector.collect("g4", translator
                .translate("graph g4 { n1->n2; n1->n3; n2->n4; n2->n5; n3->n6; n3->n7; n6->n7; n1->n6; }"), "png");
    }

    @Test
    public void p5() throws RecognitionException, IOException {
        imageCollector.collect("g5", translator.translate("graph g4 { n1->n2->n3->n1; n2->n4->n5->n2; n3->n5->n6->n3; "
                + "n4->n7->n8->n4; n5->n8->n9->n5; n6->n9->n10->n6; }"), "png");
    }

}
