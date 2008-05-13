package org.modsl.core.lang.uml;

import java.io.IOException;

import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.modsl.core.agt.model.Pt;
import org.modsl.core.lang.SVGCollector;

public class FR2LayoutVisitorTest extends AbstractUMLTest {

    static SVGCollector svgCollector = new SVGCollector("etc/svg-out", "uml_fr2");

    void process(String s, Pt reqSize) throws RecognitionException, IOException {
        String result = processor.process(s, reqSize);
        svgCollector.collect(processor.getGraph().getName(), result, reqSize);
    }

    @Test
    public void process1() throws Exception {
        process("collab g1 { n1->n2.m1; n1->n3.m2->n4.m3->n5.m4; n2->n3.m5; n5->n8.m6->n9.m7; n9->n4.m8; n8->n4.m9; }", new Pt(400,
                320));
    }

}
