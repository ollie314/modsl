package org.modsl.core.lang.uml;

import java.io.IOException;

import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.modsl.core.agt.model.Pt;
import org.modsl.core.lang.SVGCollector;

public class MoreUMLProcessorTest extends AbstractUMLTest {

    static SVGCollector svgCollector = new SVGCollector("etc/svg-out", "uml_fr2");

    void process(String s, Pt reqSize) throws RecognitionException, IOException {
        String result = processor.process(s, reqSize);
        svgCollector.collect(processor.getGraph().getName(), result, reqSize);
    }

    @Test
    public void process1() throws Exception {
        process("collab g1 { node1->node2.method1(); node1->node3.method2()->node4.method3()->node5.method4(); "
                + "node2->node3.method5(); node5->node8.method6()->node9.method7(); node9->node4.method8(); "
                + "node8->node4.method9(); }", new Pt(800, 320));
    }

}
