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
import org.modsl.core.agt.render.image.GraphRenderVisitor;
import org.modsl.core.agt.render.image.NodeRenderVisitor;

public class BasicTranslator {

    public BufferedImage translate(String str) throws RecognitionException {

        ANTLRStringStream input = new ANTLRStringStream(str);
        BasicLexer lexer = new BasicLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        BasicParser parser = new BasicParser(tokens);

        parser.graph();

        Graph graph = parser.graph;

        graph.accept(new SimpleNodeLabelLayoutVisitor().type(BasicMetaType.NODE));
        graph.accept(new FRLayoutVisitor().type(BasicMetaType.GRAPH));

        graph.rescale();

        BufferedImage img = new BufferedImage((int) graph.getSize().x, (int) graph.getSize().y, BufferedImage.TYPE_INT_RGB);

        graph.accept(new GraphRenderVisitor().image(img).type(BasicMetaType.GRAPH));
        graph.accept(new NodeRenderVisitor().image(img).type(BasicMetaType.NODE));

        img.getGraphics().dispose();

        return img;
    }

}
