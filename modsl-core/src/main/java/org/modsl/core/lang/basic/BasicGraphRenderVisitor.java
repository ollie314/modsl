package org.modsl.core.lang.basic;

import java.awt.Color;
import java.awt.RenderingHints;

import org.modsl.core.agt.model.Graph;

public class BasicGraphRenderVisitor extends AbstractRenderVisitor {

    @Override
    public void apply(Graph graph) {
        
        RenderingHints rh = new RenderingHints(null);
        
        rh.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        g.setRenderingHints(rh);
        
        g.setColor(graph.getType().getStyle().getFillColor());
        g.fillRect(0, 0, width, height);
        
        g.setColor(Color.blue);
        g.fillOval(0, 0, 199, 199);
        
    }

}
