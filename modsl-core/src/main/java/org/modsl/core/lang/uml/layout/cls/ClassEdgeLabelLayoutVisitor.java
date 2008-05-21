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

package org.modsl.core.lang.uml.layout.cls;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.modsl.core.agt.layout.AbstractNonConfigurableLayoutVisitor;
import org.modsl.core.agt.model.Edge;
import org.modsl.core.agt.model.EdgeLabel;
import org.modsl.core.agt.model.Graph;
import org.modsl.core.agt.model.MetaType;
import org.modsl.core.lang.uml.UMLMetaType;

/**
 * Edge label placement
 * @author avishnyakov
 */
public class ClassEdgeLabelLayoutVisitor extends AbstractNonConfigurableLayoutVisitor {

    public ClassEdgeLabelLayoutVisitor(MetaType type) {
        super(type);
    }

    @Override
    public void apply(Edge edge) {
    	
    	EdgeLabel from = edge.getLabels(UMLMetaType.CLASS_MULTIPLICITY_FROM_EDGE_LABEL).get(0);
    	
    	EdgeLabel to = edge.getLabels(UMLMetaType.CLASS_MULTIPLICITY_TO_EDGE_LABEL).get(0); 

    	/*
        List<EdgeLabel> labels = graph.getEdgeLabels();

        if (labels.isEmpty()) {
            return;
        }

        for (EdgeLabel label : labels) {
            Edge edge = label.getParent();
            label.setAnchor1(edge.getLastBend());
            label.setAnchor2(edge.getNode2());
            label.setOffset(0d, 0d);
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
                label.setOffset(0d, offset);
                beforeLast = last;
                last = label;
            }
        }
*/
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