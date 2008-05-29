package org.modsl.core.lang.basic;

import java.awt.image.BufferedImage;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.modsl.antlr.basic.BasicLexer;
import org.modsl.antlr.basic.BasicParser;
import org.modsl.core.agt.layout.SimpleNodeLabelLayoutVisitor;
import org.modsl.core.agt.layout.fr.FRLayoutVisitor;
import org.modsl.core.agt.model.Graph;
import org.modsl.core.agt.render.AbstractRenderVisitor;
import org.modsl.core.agt.render.Java2DRenderVisitor;

public class BasicTranslator {

    public BufferedImage translate(String str) throws RecognitionException {

        ANTLRStringStream input = new ANTLRStringStream(str);
        BasicLexer lexer = new BasicLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        BasicParser parser = new BasicParser(tokens);

        parser.graph();

        Graph graph = parser.graph;

        graph.accept(new SimpleNodeLabelLayoutVisitor(BasicMetaType.NODE));
        graph.accept(new FRLayoutVisitor(BasicMetaType.GRAPH));

        graph.rescale();

        AbstractRenderVisitor arv = new Java2DRenderVisitor();
        graph.accept(arv);

        return arv.getImage();
    }

}
