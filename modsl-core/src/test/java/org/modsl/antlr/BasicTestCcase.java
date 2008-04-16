package org.modsl.antlr;

import java.io.IOException;

import org.antlr.runtime.RecognitionException;

public class BasicTestCcase extends AbstractModSLAntlrTestCase {
	public void testNewline() throws IOException, RecognitionException {
		ModSLParser parser = createParser("\n");
		parser.line();
	}

	public void testCRLF() throws IOException, RecognitionException {
		ModSLParser parser = createParser("\r\n");
		parser.line();
	}

}
