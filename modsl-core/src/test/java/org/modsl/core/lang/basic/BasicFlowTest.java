package org.modsl.core.lang.basic;

import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.modsl.core.agt.model.Node;

public class BasicFlowTest extends AbstractBasicTest {

    @Test
    public void flow() throws RecognitionException {
        Node<BasicType> root = parse("graph g { n1->n2; n1->n3->n4->n5; n2->n3; }");
    }

}
