package org.modsl.antlr;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.antlr.runtime.RecognitionException;
import org.junit.Test;

public class BasicTest extends AbstractAntlrTest {

	@Test
	public void newline() throws IOException, RecognitionException {
		ModSLParser parser = createParser("\n");
		List<String> result = parser.line();
		assertTrue(result.isEmpty());
	}

	@Test
	public void CRLF() throws IOException, RecognitionException {
		ModSLParser parser = createParser("\r\n");
		List<String> result = parser.line();
		assertTrue(result.isEmpty());
	}

}
