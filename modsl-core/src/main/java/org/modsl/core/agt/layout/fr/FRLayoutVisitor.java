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

package org.modsl.core.agt.layout.fr;

import static java.lang.Math.abs;
import static java.lang.Math.log10;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.sqrt;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.modsl.core.agt.layout.AbstractLayoutVisitor;
import org.modsl.core.agt.model.AbstractBox;
import org.modsl.core.agt.model.Edge;
import org.modsl.core.agt.model.Graph;
import org.modsl.core.agt.model.MetaType;
import org.modsl.core.agt.model.Node;
import org.modsl.core.agt.model.Pt;

/**
 * Modified Fruchterman-Rheingold layout algorithm
 * @author avishnyakov
 */
public class FRLayoutVisitor extends AbstractLayoutVisitor {

    @SuppressWarnings("unused")
    Logger log = Logger.getLogger(this.getClass());

    double temp, kForce, kAttraction, kRepulsion;
    double tempMultiplier, attractionMultiplier, repulsionMultiplier;
    double minWeight = Double.MAX_VALUE, maxWeight = -Double.MAX_VALUE;
    int maxIterations;
    Random random;
    Graph graph;
    Pt req;
    List<Bar> bars = new LinkedList<Bar>();

    public FRLayoutVisitor(MetaType type) {
        super(type);
    }

    @Override
    public void apply(Graph graph) {

        this.graph = graph;
        this.req = getOrEsimateGraphReqSize();

        random = new Random(graph.getName().hashCode());
        graph.randomize(random);

        //calcWeights();

        temp = max((req.x + req.y) * tempMultiplier, Pt.EPSILON);
        kForce = max(sqrt(req.x * req.y / graph.getNodes().size()), Pt.EPSILON);
        kAttraction = attractionMultiplier * kForce;
        kRepulsion = repulsionMultiplier * kForce;

        for (int iterCurrent = 0; iterCurrent < maxIterations; iterCurrent++) {
            zeroDisp();
            repulsion();
            attraction();
            //weights();
            // bars();
            // grid();
            moveVertexes();
            reduceTemperature(iterCurrent, maxIterations);
        }

    }

    void attraction() {
        for (Edge e : graph.getEdges()) {
            Pt delta = getCtrDelta(e.getNode1(), e.getNode2());
            double dl = delta.lenSafe();
            Pt f = delta.div(dl).mult(attractionForce(dl));
            e.getNode1().getDisp().decBy(f);
            e.getNode2().getDisp().incBy(f);
            // log.debug("# atr " + e.getNode1() + " = -" + f + ", " +
            // e.getNode2() + " = " + f);
        }
    }

    double attractionForce(double dist) {
        return dist * dist / kAttraction;
    }

    void bars() {
        for (Node n : graph.getNodes()) {
            for (Bar b : bars) {
                if (n.getIndex() == b.getIndex()) {
                    Pt delta = new Pt(0d, 0d);
                    if (b.isVertical()) {
                        delta.y = n.getPos().y - b.getPos().y;
                    } else {
                        delta.x = n.getPos().x - b.getPos().x;
                    }
                    double dl = delta.lenSafe();
                    n.getDisp().decBy(delta.div(dl).mult(attractionForce(dl)));
                }
            }
        }
    }

    void calcWeights() {
        for (Node n : graph.getNodes()) {
            minWeight = min(minWeight, n.getWeight());
            maxWeight = max(maxWeight, n.getWeight());
        }
    }

    @Override
    public String getConfigName() {
        return "fr_layout";
    }

    Pt getCtrDelta(AbstractBox<?> n1, AbstractBox<?> n2) {
        Pt delta = n1.getCtrPos().minus(n2.getCtrPos());
        if (delta.len() < Pt.EPSILON) { 
            delta.randomize(random, new Pt(1d, 1d));
        }
        return delta;
    }
    
    Pt getOrEsimateGraphReqSize() {
        if (graph.getReqSize().equals(new Pt(0d, 0d))) {
            double area = 0d;
            double gr = (1d + sqrt(5d)) / 2d;
            for (Node n : graph.getNodes()) {
                area += n.getSize().x * n.getSize().y;
            }
            double h = sqrt(area * 3d * Math.log(graph.getEdges().size())/ gr)  + 1d;
            double w = gr * h + 1d;
            Pt rs = new Pt(w, h);
            graph.setReqSize(rs);
            return rs;
        } else {
            return graph.getReqSize();
        }

    }

    Pt getPortDelta(AbstractBox<?> n1, AbstractBox<?> n2) {
        Pt delta = n1.getPortDelta(n2);
        if (delta.len() < Pt.EPSILON) { 
            delta.randomize(random, new Pt(1d, 1d));
        }
        return delta;
    }

    void moveVertexes() {
        for (Node n : graph.getNodes()) {
            Pt delta = n.getDisp();
            double dl = delta.lenSafe();
            Pt f = delta.div(dl).mult(min(dl, temp));
            n.getPos().incBy(f);
            // log.debug("# !mv " + delta + " " + dl + " " + n + " = " + f);
            n.getPos().x = min(req.x - n.getSize().x, max(0d, n.getPos().x));
            n.getPos().y = min(req.y - n.getSize().y, max(0d, n.getPos().y));
        }
    }

    void reduceTemperature(int iterCurrent, int iterMax) {
        temp *= (1d - (double) iterCurrent / iterMax);
    }

    void repulsion() {
        for (Node n1 : graph.getNodes()) {
            for (Node n2 : graph.getNodes()) {
                if (n1 != n2) {
                    Pt delta = getPortDelta(n1, n2);
                    double dl = delta.lenSafe();
                    Pt f = delta.div(dl).mult(repulsionForce(dl));
                    n1.getDisp().incBy(f);
                    // log.debug("# rep " + n1 + " = " + f);
                }
            }
        }
    }

    /*
     * void grid() { for (Node n1 : graph.getNodes()) { Node n =
     * graph.getNodes().get(0); double nlen = Double.MAX_VALUE; for (Node n2 :
     * graph.getNodes()) { if (n1 != n2) { double l =
     * n1.getPos().minus(n2.getPos()).len(); if (l < nlen) { n = n2; nlen = l; } } }
     * Pt delta = n1.getPos().minus(n.getPos()); if (abs(n1.getPos().x -
     * n.getPos().x) < abs(n1.getPos().y - n.getPos().y)) { n1.getDisp().x -=
     * attractionForce(delta.x); n.getDisp().x += attractionForce(delta.x); }
     * else { n1.getDisp().y -= attractionForce(delta.y); n.getDisp().y +=
     * attractionForce(delta.y); } } }
     */

    double repulsionForce(double dist) {
        return kRepulsion * kRepulsion / dist;
    }

    @Override
    public void setLayoutConfig(Map<String, String> propMap) {
        maxIterations = Integer.parseInt(propMap.get("maxIterations"));
        tempMultiplier = Double.parseDouble(propMap.get("tempMultiplier"));
        attractionMultiplier = Double.parseDouble(propMap.get("attractionMultiplier"));
        repulsionMultiplier = Double.parseDouble(propMap.get("repulsionMultiplier"));
    }

    double weightAForce(double dist) {
        return dist * dist * dist / kAttraction;
    }

    double weightRForce(double dist) {
        return kRepulsion * kRepulsion / dist;
    }

    void weights() {
        if (abs(maxWeight - minWeight) > Pt.EPSILON) {
            for (Node n : graph.getNodes()) {

                // rep
                Pt anchor = new Pt();
                if (abs(n.getWeight() - minWeight) < Pt.EPSILON) {
                    anchor = graph.getReqSize().minus(n.getSize());
                } else if (abs(n.getWeight() - maxWeight) < Pt.EPSILON) {
                    anchor.x = anchor.y = 0d;
                } else {
                    continue;
                }
                Pt delta = n.getPos().minus(anchor);
                double dl = delta.lenSafe();
                Pt f = delta.div(dl).mult(weightRForce(dl));
                n.getDisp().incBy(f);

                /*
                 * //attr Pt anchor = new Pt(); if (abs(n.getWeight() -
                 * minWeight) < Pt.EPSILON) { anchor.x = anchor.y = 0d; } else
                 * if (abs(n.getWeight() - maxWeight) < Pt.EPSILON) { anchor =
                 * graph.getReqSize().minus(n.getSize()); } else { continue; }
                 * Pt delta = n.getPos().minus(anchor); double dl =
                 * delta.lenSafe(); Pt f = delta.div(dl).mult(weightAForce(dl));
                 * n.getDisp().decBy(f);
                 */

                // log.debug("# wgt " + n + " = " + f);
                /*
                 * double wr = (n.getWeight() - minWeight) / (maxWeight -
                 * minWeight); double ty = wr * graph.getReqSize().y; double f =
                 * attractionForce(n.getPos().y - ty); //log.debug("# wgt " + n + " = " +
                 * f); n.getDisp().y += f;
                 */
            }
        }
    }

    void zeroDisp() {
        for (Node n1 : graph.getNodes()) {
            n1.getDisp().zero();
        }
    }

}
