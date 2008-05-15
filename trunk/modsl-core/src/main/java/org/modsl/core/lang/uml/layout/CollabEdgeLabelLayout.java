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

package org.modsl.core.lang.uml.layout;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.modsl.core.agt.common.FontTransform;
import org.modsl.core.agt.layout.AbstractNonConfigurableLayoutVisitor;
import org.modsl.core.agt.model.AbstractBox;
import org.modsl.core.agt.model.Edge;
import org.modsl.core.agt.model.Graph;
import org.modsl.core.agt.model.Label;
import org.modsl.core.agt.model.MetaType;
import org.modsl.core.agt.model.Node;
import org.modsl.core.agt.model.Pt;

/**
 * Edge label placement
 * @author avishnyakov
 */
public class CollabEdgeLabelLayout extends AbstractNonConfigurableLayoutVisitor {

    public CollabEdgeLabelLayout(MetaType type) {
        super(type);
    }

    @Override
    public void apply(final Graph graph) {

        List<Label> labels = graph.getEdgeLabels();

        if (labels.isEmpty()) {
            return;
        }

        for (Label label : labels) {
            Edge edge = (Edge) label.getParent();
            Pt midPoint = getMidPoint(edge);
            FontTransform ft = edge.getType().getConfig().getFt();
            label.setSize(new Pt(ft.getStringWidth(edge.getName()), ft.getHeight()));
            label.setPos(midPoint.minus(new Pt(label.getSize().x / 2d, label.getSize().y / 2d)));
        }

        Collections.sort(labels, new Comparator<Label>() {

            public int compare(Label l1, Label l2) {
                return ind(l1) - ind(l2);
            }

            protected int ind(Label l) {
                return (int) (l.getPos().y * l.getPos().y + l.getPos().x);
            }

        });

        //log.debug(labels);

        Label last = null;
        Label beforeLast = null;
        for (Label label : labels) {
            if (last == null) {
                last = labels.get(0);
            } else {
                double offset = 0d;
                if (last.overlaps(label)) {
                    offset = label.getSize().y + 1;
                }
                if (beforeLast != null && beforeLast.overlaps(label)) {
                    offset = -label.getSize().y - 1;
                }
                label.getPos().y += offset;
                beforeLast = last;
                last = label;
            }
        }

    }

    /**
     * @return position of the connector's midpoint
     */
    public Pt getMidPoint(Edge edge) {
        Node n1 = edge.getNode1();
        Node n2 = edge.getNode2();
        double ratio = 1d / 2d;
        //double ratio = 1d * n1.getOutDegree() / (n1.getOutDegree() + n2.getInDegree());
        ratio = min(2d / 3d, max(ratio, 1d / 3d)); // TODO
        AbstractBox<?> b1 = n1;
        AbstractBox<?> b2 = n2;
        /*if (edge.getBends().size() > 0) {
            if (n1.getOutDegree() > n2.getInDegree()) {
                b1 = edge.getLastBend();
            } else {
                b2 = edge.getFirstBend();
            }
        }*/
        b1 = edge.getLastBend();
        b2 = edge.getNode2();
        return b1.getCtrPos().plus(b2.getCtrPos().minus(b1.getCtrPos()).mulBy(ratio));
    }

}
