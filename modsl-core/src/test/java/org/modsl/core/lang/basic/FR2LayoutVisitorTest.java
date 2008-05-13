package org.modsl.core.lang.basic;

import java.io.IOException;

import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.modsl.core.agt.model.Pt;
import org.modsl.core.lang.SVGCollector;

public class FR2LayoutVisitorTest extends AbstractBasicTest {

	static SVGCollector svgCollector = new SVGCollector("etc/svg-out", "basic_fr2");

	void process(String s, Pt reqSize) throws RecognitionException, IOException {
		String result = processor.process(s, reqSize);
		svgCollector.collect(processor.getGraph().getName(), result, reqSize);
	}

	@Test
	public void process1() throws Exception {
		process("graph g1 { n1->n2; n1->n3->n4->n5; n2->n3; n5->n8->n9; n9->n4; n8-> n4; }", new Pt(400, 320));
	}

}
