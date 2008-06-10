/**
 * Copyright 2008 Andrew Vishnyakov <avishn@gmail.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */

package org.modsl.core.agt.render.image;

import java.awt.Color;
import java.awt.GradientPaint;

import org.modsl.core.agt.model.Node;
import org.modsl.core.render.Style;

public class NodeRenderVisitor extends AbstractRenderVisitor {

    @Override
    public void apply(Node node) {
        Style s = node.getType().getStyle();
        GradientPaint gpt = new GradientPaint((int) node.getPos().x, (int) node.getPos().y, Color.white,
                (int) (node.getPos().x + node.getSize().x), (int) (node.getPos().y + node.getSize().y), s.getFillColor());
        //g.setColor(s.getFillColor());
        g.setPaint(gpt);
        g.fillRoundRect((int) node.getPos().x, (int) node.getPos().y, (int) node.getSize().x, (int) node.getSize().y, 4, 4);
        g.setColor(s.getStrokeColor());
        g.drawRoundRect((int) node.getPos().x, (int) node.getPos().y, (int) node.getSize().x, (int) node.getSize().y, 4, 4);
    }
    
}
