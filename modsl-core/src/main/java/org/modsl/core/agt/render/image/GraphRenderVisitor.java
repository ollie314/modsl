package org.modsl.core.agt.render.image;

import java.awt.RenderingHints;

import org.modsl.core.agt.model.Graph;
import org.modsl.core.render.Style;

public class GraphRenderVisitor extends AbstractRenderVisitor {

    @Override
    public void apply(Graph graph) {

        Style s = graph.getType().getStyle();

        RenderingHints rh = new RenderingHints(null);

        rh.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        g.setRenderingHints(rh);

        g.setColor(s.getFillColor());
        g.fillRect(0, 0, width, height);

        g.setFont(s.getFont());
        g.setColor(s.getFontColor());

        g.drawString("ModSL ~ http://www.modsl.org", s.getLeftPadding(), height - s.getExtHeight(1) + s.getExtBaseline(0));

    }
    
}
