package org.modsl.core.lang.uml.render.image;

import java.util.Arrays;
import java.util.List;

import org.modsl.core.agt.model.MetaType;
import org.modsl.core.agt.model.Node;
import org.modsl.core.agt.model.NodeLabel;
import org.modsl.core.agt.render.image.NodeRenderVisitor;
import org.modsl.core.lang.uml.UMLMetaType;
import org.modsl.core.render.Style;

public class ClassNodeRenderVisitor extends NodeRenderVisitor {

    @Override
    public void apply(Node node) {

        super.apply(node);

        Style s = node.getType().getStyle();
        g.setColor(s.getStrokeColor());

        int x1 = (int) node.getPos().x;
        int x2 = (int) (node.getPos().x + node.getSize().x);
        int y = (int) (node.getPos().y + s.getExtHeight(1) + 1);
        g.drawLine(x1, y, x2, y);

        List<NodeLabel> mls = node.getLabels(Arrays.asList(new MetaType[] { UMLMetaType.CLASS_METHOD_NODE_LABEL,
                UMLMetaType.CLASS_STATIC_METHOD_NODE_LABEL }));

        List<NodeLabel> vls = node.getLabels(Arrays.asList(new MetaType[] { UMLMetaType.CLASS_VAR_NODE_LABEL,
                UMLMetaType.CLASS_STATIC_VAR_NODE_LABEL }));

        if (!vls.isEmpty() && !mls.isEmpty()) {
            y += UMLMetaType.CLASS_VAR_NODE_LABEL.getStyle().getExtHeight(vls.size());
            g.drawLine(x1, y, x2, y);
        }

    }
    
}
