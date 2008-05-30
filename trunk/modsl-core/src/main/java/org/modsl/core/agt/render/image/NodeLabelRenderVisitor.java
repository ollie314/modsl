package org.modsl.core.agt.render.image;

import org.modsl.core.agt.model.NodeLabel;
import org.modsl.core.render.Style;

public class NodeLabelRenderVisitor extends AbstractRenderVisitor {

    @Override
    public void apply(NodeLabel label) {
        Style s = label.getType().getStyle();
        g.setColor(s.getFillColor());
        g.setFont(s.getFont());
        g.drawString(label.getName(), (float) label.getTextPos().x, (float) label.getTextPos().y);
    }

}
