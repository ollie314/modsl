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

package org.modsl.core.agt.layout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.modsl.core.agt.model.Edge;
import org.modsl.core.agt.model.Node;

public class SugiyamaLayout extends AbstractNonConfigurableLayout {

    @Override
    public void apply(Node<?> root) {
        removeCycles(root);

        undoRemoveCycles(root);
    }

    void undoRemoveCycles(Node<?> root) {
        for (Edge<?> e : root.getEdges()) {
            e.setReverted(false);
        }
    }

    void removeCycles(Node<?> root) {
        List<Node<?>> nodes = sortByOutDegree(root);
        Set<Edge<?>> removed = new HashSet<Edge<?>>(root.getEdges().size());
        for (Node<?> n : nodes) {
            for (Edge<?> in : n.getInEdges()) {
                if (!removed.contains(in)) {
                    in.setReverted(true);
                    removed.add(in);
                }
            }
            for (Edge<?> out : n.getOutEdges()) {
                if (!removed.contains(out)) {
                    removed.add(out);
                }
            }
        }
    }

    List<Node<?>> sortByOutDegree(Node<?> root) {
        List<Node<?>> nodes = new ArrayList<Node<?>>(root.getNodes());
        Collections.sort(nodes, new Comparator<Node<?>>() {
            public int compare(Node<?> n1, Node<?> n2) {
                return n2.getOutDegree() - n1.getOutDegree();
            }
        });
        return nodes;
    }

}
