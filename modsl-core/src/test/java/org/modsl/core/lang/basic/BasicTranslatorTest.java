package org.modsl.core.lang.basic;

import java.io.IOException;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.modsl.antlr.basic.BasicLexer;
import org.modsl.antlr.basic.BasicParser;
import org.modsl.core.AbstractModSLTest;
import org.modsl.core.ImageCollector;
import org.modsl.core.agt.layout.AbstractLayoutVisitor;
import org.modsl.core.agt.layout.SimpleNodeLabelLayoutVisitor;
import org.modsl.core.agt.layout.fr.FRLayoutVisitor;
import org.modsl.core.agt.model.Graph;
import org.modsl.core.agt.render.AbstractRenderVisitor;
import org.modsl.core.agt.render.Java2DRenderVisitor;
import org.modsl.core.render.StyleLoader;

public class BasicTranslatorTest extends AbstractModSLTest {

    static ImageCollector imageCollector = new ImageCollector("etc/png-out", "basic");

    static {
        StyleLoader stl = new StyleLoader();
        stl.load("cfg/basic:cfg", "basic", BasicMetaType.class);
    }

    @Test
    public void test() throws RecognitionException, IOException {

        ANTLRStringStream input = new ANTLRStringStream("graph g1 { n1->n2; n1->n3->n4->n5; n2->n3; n5->n8->n9; n9->n4; n8-> n4; }");
        BasicLexer lexer = new BasicLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        BasicParser parser = new BasicParser(tokens);
        parser.graph();
        Graph graph = parser.graph;

        for (AbstractLayoutVisitor layout : getLayoutVisitors()) {
            graph.accept(layout);
        }

        graph.rescale();

        AbstractRenderVisitor arv = new Java2DRenderVisitor();
        graph.accept(arv);

        imageCollector.collect(graph.getName(), arv.getBytes(), "png");

    }

    private AbstractLayoutVisitor[] getLayoutVisitors() {
        return new AbstractLayoutVisitor[] { new SimpleNodeLabelLayoutVisitor(BasicMetaType.NODE),
                new FRLayoutVisitor(BasicMetaType.GRAPH) };
    }
}
