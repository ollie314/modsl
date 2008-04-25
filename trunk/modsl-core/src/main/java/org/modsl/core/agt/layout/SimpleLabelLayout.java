package org.modsl.core.agt.layout;

import org.modsl.core.agt.model.Edge;
import org.modsl.core.agt.model.FontTransform;
import org.modsl.core.agt.model.Node;
import org.modsl.core.agt.model.Pt;

/**
 * Does simple node size calculation based on this node's text height and width.
 * 
 * @author avishnyakov
 * 
 */
public class SimpleLabelLayout extends AbstractNonConfigurableLayout {

    @Override
    public void apply(Node<?> node) {
        FontTransform ft = node.getType().getFontTransform();
        node.setSize(new Pt(ft.getExtStringWidth(node.getName()), ft.getExtHeight(1)));
    }

    @Override
    public void apply(Edge<?> edge) {
        // NO OP
    }

}
