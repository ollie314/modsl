package org.modsl.core.agt.render.image;

import java.awt.geom.RoundRectangle2D;

import org.modsl.core.agt.model.Node;
import org.modsl.core.render.Style;

public class NodeRenderVisitor extends AbstractRenderVisitor {

    @Override
    public void apply(Node node) {
        Style s = node.getType().getStyle();
        g.setColor(s.getFillColor());
        g.fill(new RoundRectangle2D.Double(node.getPos().x, node.getPos().y, node.getSize().x, node.getSize().y, 3d, 3d));
    }

}
