package org.modsl.core.agt.layout;

import java.util.Map;

import org.modsl.core.agt.model.FontTransform;
import org.modsl.core.agt.model.Node;
import org.modsl.core.agt.model.Pt;

/**
 * Does simple node size calculation based on this node's text height and width.
 * 
 * @author avishnyakov
 * 
 */
public class SimpleLabelLayout implements Layout {

    @Override
    public void apply(Node<?> node) {
        FontTransform ft = node.getType().getFontTransform();
        node.setSize(new Pt(ft.getExtStringWidth(node.getName()), ft.getExtHeight(1)));
    }


    @Override
    public String getConfigName() {
        return null;
    }


    @Override
    public void setConfig(Map<String, String> propMap) {
        
    }

}
