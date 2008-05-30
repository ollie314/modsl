package org.modsl.core.agt.render.image;

import java.awt.geom.Rectangle2D;

import org.modsl.core.agt.model.Node;
import org.modsl.core.render.Style;

public class NodeRenderVisitor extends AbstractRenderVisitor {

    @Override
    public void apply(Node node) {
        Style s = node.getType().getStyle();
        g.setColor(s.getFillColor());
        g.fill(new Rectangle2D.Double(node.getPos().x, node.getPos().y, node.getSize().x, node.getSize().y));
        g.setColor(s.getStrokeColor());
        g.draw(new Rectangle2D.Double(node.getPos().x, node.getPos().y, node.getSize().x, node.getSize().y));
    }

}
