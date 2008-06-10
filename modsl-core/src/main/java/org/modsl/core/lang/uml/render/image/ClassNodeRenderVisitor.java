package org.modsl.core.lang.uml.render.image;

import org.modsl.core.agt.model.Node;
import org.modsl.core.agt.render.image.NodeRenderVisitor;
import org.modsl.core.render.Style;

public class ClassNodeRenderVisitor extends NodeRenderVisitor {

    @Override
    public void apply(Node node) {
        super.apply(node);
        Style s = node.getType().getStyle();
        g.setColor(s.getStrokeColor());
        int hly = (int) (node.getPos().y + s.getExtHeight(1) + 1);
        g.drawLine((int) node.getPos().x, hly, (int) (node.getPos().x + node.getSize().x), hly);
    }
    
}
