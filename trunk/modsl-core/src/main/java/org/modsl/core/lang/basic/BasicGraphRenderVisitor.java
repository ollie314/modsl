package org.modsl.core.lang.basic;

import java.awt.Color;

import org.modsl.core.agt.model.Graph;

public class BasicGraphRenderVisitor extends AbstractRenderVisitor {

    @Override
    public void apply(Graph bend) {
        g.setColor(Color.blue);
        g.fillOval(0, 0, 199, 199);
    }

}
