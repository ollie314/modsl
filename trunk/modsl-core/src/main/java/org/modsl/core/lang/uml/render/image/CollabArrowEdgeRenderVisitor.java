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

package org.modsl.core.lang.uml.render.image;

import static java.lang.Math.PI;

import org.modsl.core.agt.model.Edge;
import org.modsl.core.agt.model.EdgeLabel;
import org.modsl.core.lang.uml.UMLMetaType;
import org.modsl.core.render.Style;

public class CollabArrowEdgeRenderVisitor extends AbstractArrowEdgeRenderVisitor {

    @Override
    public void apply(Edge e) {
        super.apply(e);
        drawLabels(e);
    }

    void drawLabels(Edge e) {

        EdgeLabel l = e.getLabels(UMLMetaType.COLLAB_EDGE_LABEL).get(0);
        Style s = e.getType().getStyle();

        g.setColor(s.getFillColor());
        g.fillRect((int) l.getPos().x, (int) l.getPos().y, (int) l.getSize().x, (int) l.getSize().y);

        g.setFont(s.getFont());
        g.setColor(s.getFontColor());
        g.drawString(l.getName(), (float) l.getTextPos().x, (float) l.getTextPos().y);

    }

    @Override
    protected double getArrowAngle() {
        return PI / 5d;
    }

    @Override
    protected double getArrowLength() {
        return UMLMetaType.COLLAB_EDGE.getStyle().getArrowLength();
    }

}
