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

import org.modsl.core.agt.model.AbstractConnectedBox;

public class SugiyamaLayerStack {

    // TODO dynamic separation based on label size?
    protected static final double X_SEPARATION = 60d;
    protected static final double Y_SEPARATION = 60d;

    private List<List<AbstractConnectedBox>> layers;
    private Map<AbstractConnectedBox, Integer> nodeMap;

    SugiyamaLayerStack(int h, int nodeSize) {
        layers = new ArrayList<List<AbstractConnectedBox>>(h);
        for (int i = 0; i < h; i++) {
            layers.add(new ArrayList<AbstractConnectedBox>(nodeSize / h + 1));
        }
        nodeMap = new HashMap<AbstractConnectedBox, Integer>(nodeSize);
    }

    protected List<AbstractConnectedBox> getElements(int layer) {
        return layers.get(layer);
    }

    protected int size() {
        return layers.size();
    }

    void add(AbstractConnectedBox n1, int layerIndex) {
        layers.get(layerIndex).add(n1);
        nodeMap.put(n1, layerIndex);
    }

    int barycenter(List<AbstractConnectedBox> ln) {
        if (ln.size() == 0) {
            return 0;
        } else {
            double bc = 0;
            for (AbstractConnectedBox n : ln) {
                bc += n.getIndex();
            }
            return (int) round(bc / ln.size());
        }
    }

    List<AbstractConnectedBox> getConnectedTo(AbstractConnectedBox n1, int layerIndex) {
        List<AbstractConnectedBox> ln = new LinkedList<AbstractConnectedBox>();
        for (AbstractConnectedBox n2 : layers.get(layerIndex)) {
            if (n1.isConnectedTo(n2)) {
                ln.add(n2);
            }
        }
        return ln;
    }

    int getLayer(AbstractConnectedBox n) {
        Integer l = nodeMap.get(n);
        if (l == null) {
            return 0;
        } else {
            return l;
        }
    }

    void initIndexes() {
        for (List<AbstractConnectedBox> l : layers) {
            setOrderedIndexes(l);
        }
    }

    void layerHeights() {
        double currOffset = 0d;
        for (int l = 0; l < layers.size(); l++) {
            List<AbstractConnectedBox> ln = layers.get(l);
            double maxh = maxHeight(ln);
            for (AbstractConnectedBox n : ln) {
                if (n.isVirtual()) {
                    n.getPos().y = currOffset + maxHeight(ln) / 2d;
                } else {
                    n.getPos().y = currOffset;
                }
            }
            currOffset += maxh + Y_SEPARATION;
        }
    }

    double maxHeight(List<AbstractConnectedBox> ln) {
        double mh = 0d;
        for (AbstractConnectedBox n : ln) {
            mh = max(mh, n.getSize().y);
        }
        return mh;
    }

    void reduceCrossings() {
        for (int round = 0; round < 100; round++) {
            if (round % 2 == 0) {
                for (int l = 0; l < layers.size() - 1; l++) {
                    reduceCrossings2L(l, l + 1);
                }
            } else {
                for (int l = layers.size() - 1; l > 0; l--) {
                    reduceCrossings2L(l, l - 1);
                }
            }
        }
    }

    void reduceCrossings2L(int staticLayer, int layerToShuffle) {
        final List<AbstractConnectedBox> shuffle = layers.get(layerToShuffle);
        for (AbstractConnectedBox n : shuffle) {
            List<AbstractConnectedBox> neighbors = getConnectedTo(n, staticLayer);
            int bc = barycenter(neighbors);
            n.setIndex(bc);
        }
        Collections.sort(shuffle, new Comparator<AbstractConnectedBox>() {
            public int compare(AbstractConnectedBox n1, AbstractConnectedBox n2) {
                return n1.getIndex() - n2.getIndex();
            }
        });
        setOrderedIndexes(shuffle);
    }

    void setOrderedIndexes(List<AbstractConnectedBox> ln) {
        for (int i = 0; i < ln.size(); i++) {
            ln.get(i).setIndex(i);
        }
    }

    void xPositions() {
        double maxx = 0;
        double x[] = new double[layers.size()];
        for (int l = 0; l < layers.size(); l++) {
            double currOffset = 0d;
            for (AbstractConnectedBox n : layers.get(l)) {
                currOffset += n.getSize().x + X_SEPARATION;
            }
            x[l] = currOffset - X_SEPARATION;
            maxx = max(maxx, x[l]);
        }
        for (int l = 0; l < layers.size(); l++) {
            double currOffset = (maxx - x[l]) / 2d;
            for (AbstractConnectedBox n : layers.get(l)) {
                n.getPos().x = currOffset;
                currOffset += n.getSize().x + X_SEPARATION;
            }
        }
    }

    @Override
    public String toString() {
        return nodeMap.toString();
    }

}