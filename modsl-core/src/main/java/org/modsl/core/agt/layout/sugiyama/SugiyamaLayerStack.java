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

import org.apache.log4j.Logger;
import org.modsl.core.agt.model.AbstractBox;

/**
 * A stack of layers for the Sugiyama layout
 * @author avishnyakov
 */
public class SugiyamaLayerStack {

    protected Logger log = Logger.getLogger(getClass());

    protected int maxSweeps;

    protected double xSeparation;
    protected double ySeparation;

    private List<List<AbstractBox<?>>> layers;
    private Map<AbstractBox<?>, Integer> nodeMap;

    void init(int h, int nodeSize) {
        layers = new ArrayList<List<AbstractBox<?>>>(h);
        for (int i = 0; i < h; i++) {
            layers.add(new ArrayList<AbstractBox<?>>(nodeSize / h + 1));
        }
        nodeMap = new HashMap<AbstractBox<?>, Integer>(nodeSize);
    }

    void add(AbstractBox<?> n1, int layerIndex) {
        layers.get(layerIndex).add(n1);
        nodeMap.put(n1, layerIndex);
    }

    int barycenter(List<AbstractBox<?>> ln) {
        if (ln.size() == 0) {
            return 0;
        } else {
            double bc = 0;
            for (AbstractBox<?> n : ln) {
                bc += n.getIndex();
            }
            return (int) round(bc / ln.size());
        }
    }

    List<AbstractBox<?>> getConnectedTo(AbstractBox<?> n1, int layerIndex) {
        List<AbstractBox<?>> ln = new LinkedList<AbstractBox<?>>();
        if (layerIndex < layers.size() && layerIndex >= 0) {
            for (AbstractBox<?> n2 : layers.get(layerIndex)) {
                if (n1.isConnectedTo(n2)) {
                    ln.add(n2);
                }
            }
        }
        return ln;
    }

    protected List<AbstractBox<?>> getElements(int layer) {
        return layers.get(layer);
    }

    int getLayer(AbstractBox<?> n) {
        Integer l = nodeMap.get(n);
        if (l == null) {
            return 0;
        } else {
            return l;
        }
    }

    void initIndexes() {
        for (List<AbstractBox<?>> l : layers) {
            Collections.sort(l, new Comparator<AbstractBox<?>>() {
                public int compare(AbstractBox<?> n1, AbstractBox<?> n2) {
                    return n1.getIndex() - n2.getIndex();
                }
            });
            setOrderedIndexes(l);
        }
    }

    void layerHeights() {
        double currOffset = 0d;
        for (int l = 0; l < layers.size(); l++) {
            List<AbstractBox<?>> ln = layers.get(l);
            double maxh = maxHeight(ln);
            for (AbstractBox<?> n : ln) {
                if (n.isVirtual()) {
                    n.getPos().y = currOffset + maxHeight(ln) / 2d;
                } else {
                    n.getPos().y = currOffset;
                }
            }
            currOffset += maxh + ySeparation;
        }
    }

    double maxHeight(List<AbstractBox<?>> ln) {
        double mh = 0d;
        for (AbstractBox<?> n : ln) {
            mh = max(mh, n.getSize().y);
        }
        return mh;
    }

    void reduceCrossings() {
        for (int round = 0; round < maxSweeps; round++) {
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
        final List<AbstractBox<?>> shuffle = layers.get(layerToShuffle);
        for (AbstractBox<?> n : shuffle) {
            List<AbstractBox<?>> neighbors = getConnectedTo(n, staticLayer);
            int bc = barycenter(neighbors);
            n.setIndex(bc);
        }
        Collections.sort(shuffle, new Comparator<AbstractBox<?>>() {
            public int compare(AbstractBox<?> n1, AbstractBox<?> n2) {
                return n1.getIndex() - n2.getIndex();
            }
        });
        setOrderedIndexes(shuffle);
    }

    void setOrderedIndexes(List<AbstractBox<?>> ln) {
        for (int i = 0; i < ln.size(); i++) {
            ln.get(i).setIndex(i);
        }
    }

    protected int size() {
        return layers.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName());
        sb.append("(").append(layers.size()).append(")");
        int lc = 0;
        for (List<AbstractBox<?>> l : layers) {
            sb.append("\n\t").append(lc++).append(" # ").append(l);
        }
        return sb.toString();
    }

    void xPositions() {
        double maxx = 0;
        double x[] = new double[layers.size()];
        for (int l = 0; l < layers.size(); l++) {
            double currOffset = 0d;
            for (AbstractBox<?> n : layers.get(l)) {
                currOffset += n.getSize().x + xSeparation;
            }
            x[l] = currOffset - xSeparation;
            maxx = max(maxx, x[l]);
        }
        for (int l = 0; l < layers.size(); l++) {
            double currOffset = (maxx - x[l]) / 2d;
            for (AbstractBox<?> n : layers.get(l)) {
                n.getPos().x = currOffset;
                currOffset += n.getSize().x + xSeparation;
            }
        }
    }

    void xPositionsAlt() {
        for (int round = 0; round < maxSweeps; round++) {
            if (round % 2 == 0) {
                for (int l = 0; l < layers.size() - 1; l++) {
                    adjustPosX(l, l + 1, l + 2);
                }
            } else {
                for (int l = layers.size() - 1; l > 0; l--) {
                    adjustPosX(l, l - 1, l - 2);
                }
            }
        }
    }

    void adjustPosX(int staticLayer1, int flexLayer, int staticLayer2) {
        List<AbstractBox<?>> flex = layers.get(flexLayer);
        double currOffset = 0d;
        for (AbstractBox<?> n : flex) {
            List<AbstractBox<?>> neighbors1 = getConnectedTo(n, staticLayer1);
            List<AbstractBox<?>> neighbors2 = getConnectedTo(n, staticLayer2);
            neighbors1.addAll(neighbors2);
            double bc1 = barycenterX(n.getCtrPos().x, neighbors1) - n.getSize().x / 2d;
            //            double bc2 = barycenterX(bc1, neighbors2) - n.getSize().x / 2d;
            n.getPos().x = max(currOffset, bc1);
            currOffset = n.getPos().x + n.getSize().x + xSeparation;
            //if ("c3:o3".equals(n.getName())) {
            //    log.debug(n);
            //}
            //log.debug(staticLayer1 + "/" + staticLayer2 + "->" + flexLayer + " " + flex);
        }
    }

    double barycenterX(double def, List<AbstractBox<?>> ln) {
        if (ln.size() == 0) {
            return def;
        } else {
            double bc = 0;
            for (AbstractBox<?> n : ln) {
                bc += n.getCtrPos().x;
            }
            return bc / ln.size();
        }
    }
}