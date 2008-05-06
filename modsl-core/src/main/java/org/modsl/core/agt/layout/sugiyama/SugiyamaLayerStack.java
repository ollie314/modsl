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

/**
 * 
 */
package org.modsl.core.agt.layout.sugiyama;

import static java.lang.Math.max;
import static java.lang.Math.round;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.modsl.core.agt.model.Node;

public class SugiyamaLayerStack {

    private List<List<Node<?>>> layers;
    private Map<Node<?>, Integer> nodeMap;

    SugiyamaLayerStack(int h, int nodeSize) {
        layers = new ArrayList<List<Node<?>>>(h);
        for (int i = 0; i < h; i++) {
            layers.add(new ArrayList<Node<?>>(nodeSize / h + 1));
        }
        nodeMap = new HashMap<Node<?>, Integer>(nodeSize);
    }

    protected int size() {
        return layers.size();
    }

    void add(Node<?> n1, int layerIndex) {
        layers.get(layerIndex).add(n1);
        nodeMap.put(n1, layerIndex);
    }

    int barycenter(List<Node<?>> ln) {
        if (ln.size() == 0) {
            return 0;
        } else {
            double bc = 0;
            for (Node<?> n : ln) {
                bc += n.getIndex();
            }
            return (int) round(bc / ln.size());
        }
    }

    List<Node<?>> getConnectedTo(Node<?> n1, int layerIndex) {
        List<Node<?>> ln = new LinkedList<Node<?>>();
        for (Node<?> n2 : layers.get(layerIndex)) {
            if (n1.isConnectedTo(n2)) {
                ln.add(n2);
            }
        }
        return ln;
    }

    int getLayer(Node<?> n) {
        Integer l = nodeMap.get(n);
        if (l == null) {
            return 0;
        } else {
            return l;
        }
    }

    void initIndexes() {
        for (List<Node<?>> l : layers) {
            setOrderedIndexes(l);
        }
    }

    void layerHeights() {
        double currOffset = 0d;
        for (int l = 0; l < layers.size(); l++) {
            List<Node<?>> ln = layers.get(l);
            for (Node<?> n : ln) {
                n.getPos().y = currOffset;
            }
            currOffset += maxHeight(ln) + SugiyamaLayout.Y_SEPARATION;
        }
    }

    double maxHeight(List<Node<?>> ln) {
        double mh = 0d;
        for (Node<?> n : ln) {
            mh = max(mh, n.getSize().y);
        }
        return mh;
    }

    void reduceCrossings() {
        for (int round = 0; round < 100; round++) {
            if (round % 2 == 0) {
                for (int l = 0; l < layers.size()-1; l++) {
                    reduceCrossings2L(l, l + 1);
                }
            } else {
                for (int l = layers.size()-1; l > 0; l--) {
                    reduceCrossings2L(l, l - 1);
                }
            }
        }
    }

    void reduceCrossings2L(int staticLayer, int layerToShuffle) {
        final List<Node<?>> shufflel = layers.get(layerToShuffle);
        for (Node<?> n : shufflel) {
            List<Node<?>> neighbors = getConnectedTo(n, staticLayer);
            int bc = barycenter(neighbors);
            n.setIndex(bc);
        }
        Collections.sort(shufflel, new Comparator<Node<?>>() {
            public int compare(Node<?> n1, Node<?> n2) {
                return n1.getIndex() - n2.getIndex();
            }
        });
        setOrderedIndexes(shufflel);
    }

    void setOrderedIndexes(List<Node<?>> ln) {
        for (int i = 0; i < ln.size(); i++) {
            ln.get(i).setIndex(i);
        }
    }

    void xPositions() {
        for (int l = 0; l < layers.size(); l++) {
            double currOffset = 0d;
            List<Node<?>> ln = layers.get(l);
            for (Node<?> n : ln) {
                n.getPos().x = currOffset;
                currOffset += n.getSize().x + SugiyamaLayout.X_SEPARATION;
            }
        }
    }

    public List<Node<?>> getNodes(int i) {
        return layers.get(i);
    }

    @Override
    public String toString() {
        return nodeMap.toString();
    }

}