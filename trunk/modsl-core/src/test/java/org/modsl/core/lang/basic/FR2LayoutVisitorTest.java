package org.modsl.core.lang.basic;

import java.io.IOException;

import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.modsl.core.agt.model.Pt;

public class FR2LayoutVisitorTest extends AbstractBasicTest {

	@Test
	public void process3() throws RecognitionException, IOException {
		process("graph g3 { n1->n2->n3->n4->n5->n6->n7->n8; n5->n1->n3; n2->n4->n6->n8->n5->n2; "
				+ "n3->n5->n7; n6->n1->n4->n8; n6->n2->n8->n1->n7; n4->n7->n2; n8->n3->n6; n3->n7; }", new Pt(400, 320));
	}
	
}
