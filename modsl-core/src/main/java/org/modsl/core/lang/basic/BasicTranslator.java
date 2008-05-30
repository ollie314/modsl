package org.modsl.core.lang.basic;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.modsl.antlr.basic.BasicLexer;
import org.modsl.antlr.basic.BasicParser;
import org.modsl.core.agt.layout.SimpleNodeLabelLayoutVisitor;
import org.modsl.core.agt.layout.fr.FRLayoutVisitor;
import org.modsl.core.agt.model.Graph;
import org.modsl.core.agt.render.image.EdgeRenderVisitor;
import org.modsl.core.agt.render.image.GraphRenderVisitor;
import org.modsl.core.agt.render.image.NodeLabelRenderVisitor;
import org.modsl.core.agt.render.image.NodeRenderVisitor;

public class BasicTranslator {

    Graph layout(Graph graph) {
        
        graph.accept(new SimpleNodeLabelLayoutVisitor().type(BasicMetaType.NODE));
        graph.accept(new FRLayoutVisitor().type(BasicMetaType.GRAPH));
        
        graph.rescale();
        
        return graph;
        
    }

    public Graph parse(String str) throws RecognitionException {

        ANTLRStringStream input = new ANTLRStringStream(str);
        BasicLexer lexer = new BasicLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        BasicParser parser = new BasicParser(tokens);

        parser.graph();

        return parser.graph;
    }

    BufferedImage render(Graph graph) {

        BufferedImage img = new BufferedImage((int) graph.getSize().x, (int) graph.getSize().y, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) img.getGraphics();
        int w = img.getWidth();
        int h = img.getHeight();

        graph.accept(new GraphRenderVisitor().graphics(g, w, h).type(BasicMetaType.GRAPH));
        graph.accept(new EdgeRenderVisitor().graphics(g, w, h).type(BasicMetaType.EDGE));
        graph.accept(new NodeRenderVisitor().graphics(g, w, h).type(BasicMetaType.NODE));
        graph.accept(new NodeLabelRenderVisitor().graphics(g, w, h).type(BasicMetaType.NODE_LABEL));

        img.getGraphics().dispose();

        return img;

    }

    public BufferedImage translate(String str) throws RecognitionException {
        Graph graph = parse(str);
        layout(graph);
        return render(graph);
    }

}
