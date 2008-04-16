package org.modsl.antlr;

import java.io.IOException;
import java.util.List;

import org.antlr.runtime.RecognitionException;

public class BasicTestCcase extends AbstractModSLAntlrTestCase {

	public void testNewline() throws IOException, RecognitionException {
		ModSLParser parser = createParser("\n");
		List<String> result = parser.line();
		assertTrue(result.isEmpty());
	}

	public void testCRLF() throws IOException, RecognitionException {
		ModSLParser parser = createParser("\r\n");
		List<String> result = parser.line();
		assertTrue(result.isEmpty());
	}

}
