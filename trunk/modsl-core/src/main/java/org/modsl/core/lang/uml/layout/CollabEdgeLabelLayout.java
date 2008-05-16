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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.modsl.core.agt.common.FontTransform;
import org.modsl.core.agt.layout.AbstractNonConfigurableLayoutVisitor;
import org.modsl.core.agt.model.AbstractBox;
import org.modsl.core.agt.model.Edge;
import org.modsl.core.agt.model.EdgeLabel;
import org.modsl.core.agt.model.Graph;
import org.modsl.core.agt.model.Label;
import org.modsl.core.agt.model.MetaType;
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

        List<EdgeLabel> labels = graph.getEdgeLabels();

        if (labels.isEmpty()) {
            return;
        }

        for (EdgeLabel label : labels) {
            Edge edge = label.getParent();
            FontTransform ft = edge.getType().getConfig().getFt();
            label.setSize(new Pt(ft.getStringWidth(edge.getName()), ft.getHeight()));
            label.setPos(getMidPoint(label, 0));
        }

        Collections.sort(labels, new Comparator<EdgeLabel>() {

            public int compare(EdgeLabel l1, EdgeLabel l2) {
                return ind(l1) - ind(l2);
            }

            protected int ind(EdgeLabel l) {
                return (int) (l.getPos().y * l.getPos().y + l.getPos().x);
            }

        });

        //log.debug(labels);

        EdgeLabel last = null;
        EdgeLabel beforeLast = null;
        for (EdgeLabel label : labels) {
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
                label.setPos(getMidPoint(label, offset));
                beforeLast = last;
                last = label;
            }
        }

    }

    /**
     * @return position of the connector's midpoint
     */
    public Pt getMidPoint(EdgeLabel label, double offset) {
        Edge edge = label.getParent();
        AbstractBox<?> n1 = edge.getLastBend();
        AbstractBox<?> n2 = edge.getNode2();
        double ratio = 1d / 2d;
        Pt mid = n1.getCtrPos().plus(n2.getCtrPos().minus(n1.getCtrPos()).mulBy(ratio));
        mid.incBy(new Pt(offset / n1.tan(n2), offset));
        mid.decBy(new Pt(label.getSize().x / 2d, label.getSize().y / 2d));
        return mid;
    }

}

/*
 *
 *   public Pt getMidPoint(Edge edge, double offs) {
        Node n1 = edge.getNode1();
        Node n2 = edge.getNode2();
        double ratio = 1d / 2d;
        //double ratio = 1d * n1.getOutDegree() / (n1.getOutDegree() + n2.getInDegree());
        ratio = min(2d / 3d, max(ratio, 1d / 3d)); // TODO
        AbstractBox<?> b1 = n1;
        AbstractBox<?> b2 = n2;
        if (edge.getBends().size() > 0) {
            if (n1.getOutDegree() > n2.getInDegree()) {
                b1 = edge.getLastBend();
            } else {
                b2 = edge.getFirstBend();
            }
        }
        return b1.getCtrPos().plus(b2.getCtrPos().minus(b1.getCtrPos()).mulBy(ratio));
    }
*/