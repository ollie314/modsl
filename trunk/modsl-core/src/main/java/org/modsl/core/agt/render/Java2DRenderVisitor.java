/**
 * Copyright 2008 Andrew Vishnyakov <avishn@gmail.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.modsl.core.agt.render;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.modsl.core.agt.model.Bend;
import org.modsl.core.agt.model.Edge;
import org.modsl.core.agt.model.EdgeLabel;
import org.modsl.core.agt.model.Graph;
import org.modsl.core.agt.model.GraphLabel;
import org.modsl.core.agt.model.Node;
import org.modsl.core.agt.model.NodeLabel;

public class Java2DRenderVisitor extends AbstractRenderVisitor {

    BufferedImage image;
    Graphics g;

    @Override
    public BufferedImage getImage() {
        return image;
    }

    @Override
    public void in(Bend bend) {
    }

    @Override
    public void in(Edge edge) {
    }

    @Override
    public void in(EdgeLabel label) {
    }

    @Override
    public void in(Graph graph) {

        image = new BufferedImage((int) graph.getSize().x, (int) graph.getSize().y, BufferedImage.TYPE_INT_RGB);
        g = image.getGraphics();

        g.setColor(Color.blue);
        g.fillOval(0, 0, 199, 199);

    }

    @Override
    public void in(GraphLabel label) {
    }

    @Override
    public void in(Node node) {
    }

    @Override
    public void in(NodeLabel label) {
    }

    @Override
    public void out(Graph graph) {
        g.dispose();
    }

}
