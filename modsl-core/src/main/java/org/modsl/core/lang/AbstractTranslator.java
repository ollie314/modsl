package org.modsl.core.lang;

import java.awt.image.BufferedImage;

import org.antlr.runtime.RecognitionException;
import org.modsl.core.agt.model.Graph;

public abstract class AbstractTranslator {

    public BufferedImage translate(String str) throws RecognitionException {
        Graph graph = parse(str);
        layout(graph);
        return render(graph);
    }

    protected abstract Graph layout(Graph graph);

    public abstract Graph parse(String str) throws RecognitionException;

    protected abstract BufferedImage render(Graph graph);

}