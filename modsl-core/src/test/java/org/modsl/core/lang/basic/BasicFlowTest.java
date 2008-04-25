package org.modsl.core.lang.basic;

import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.modsl.core.agt.model.Node;
import org.modsl.core.agt.visitor.STVisitor;

public class BasicFlowTest extends AbstractBasicTest {

    protected static final String ROOTDIR = "cfg";
    protected static final String CFGDIR = ROOTDIR + "/basic";
    protected static final String NAME = "basic";

    @Test
    public void flow() throws RecognitionException {
        Node<BasicType> root = parse("graph g { n1->n2; n1->n3->n4->n5; n2->n3; }");
        STVisitor<BasicType> stv = new STVisitor<BasicType>(ROOTDIR + ":" + CFGDIR, NAME, 0);
        root.accept(stv);
        String result = stv.toString();
        log.debug(result);
    }

}
