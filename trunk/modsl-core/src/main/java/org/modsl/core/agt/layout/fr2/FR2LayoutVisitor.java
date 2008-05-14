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

package org.modsl.core.agt.layout.fr2;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.sqrt;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
public class FR2LayoutVisitor extends AbstractLayoutVisitor {

    @SuppressWarnings("unused")
    Logger log = Logger.getLogger(this.getClass());

    double temp, kForce, kAttraction, kRepulsion;
    double tempMultiplier, attractionMultiplier, repulsionMultiplier;
    double minWeight = Double.MAX_VALUE, maxWeight = Double.MIN_VALUE;
    int maxIterations;
    Graph graph;
    Pt req;
    List<Bar> bars = new LinkedList<Bar>();

    public FR2LayoutVisitor(MetaType type) {
        super(type);
    }

    void attraction() {
        for (Edge e : graph.getEdges()) {
            Pt delta = getDelta(e.getNode1(), e.getNode2());
            double dl = delta.lenSafe();
            e.getNode1().getDisp().decBy(delta.div(dl).mult(attractionForce(dl)));
            e.getNode2().getDisp().incBy(delta.div(dl).mult(attractionForce(dl)));
        }
    }

    double attractionForce(double dist) {
        return dist * dist / kAttraction;
    }

    @Override
    public String getConfigName() {
        return "fr2_layout";
    }

    Pt getDelta(AbstractBox<?> n1, AbstractBox<?> n2) {
        Pt delta = n1.getPortDelta(n2);
        if (delta.len() < Pt.EPSILON) {
            delta.randomize(Pt.EPSILON);
        }
        return delta;
    }

    @Override
    public void in(Graph graph) {

        if (graph.getType() != this.type) {
            return;
        }

        this.graph = graph;
        this.req = graph.getReqSize();

        graph.randomize(graph.getName().hashCode());
        graph.recalcSize();

        calcWeights();

        temp = max((req.x + req.y) * tempMultiplier, Pt.EPSILON);
        kForce = max(sqrt(req.x * req.y / graph.getNodes().size()), Pt.EPSILON);
        kAttraction = attractionMultiplier * kForce;
        kRepulsion = repulsionMultiplier * kForce;

        for (int iterCurrent = 0; iterCurrent < maxIterations; iterCurrent++) {
            zeroDisp();
            repulsion();
            attraction();
            weights();
            //bars();
            // grid();
            moveVertexes();
            reduceTemperature(iterCurrent, maxIterations);
        }

    }

    void weights() {
        double miny = graph.getMinPt().y;
        double maxy = graph.getMaxPt().y;
        for (Node n : graph.getNodes()) {
            double wr = (n.getWeight() - minWeight) / (maxWeight - minWeight);
            double ty = wr * (n.getPos().y - miny)/ (maxy - n.getSize().y - miny) + miny;
            n.getDisp().y -= attractionForce(ty);
        }
    }

    void calcWeights() {
        for (Node n : graph.getNodes()) {
            minWeight = min(minWeight, n.getWeight());
            maxWeight = max(maxWeight, n.getWeight());
        }
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

    void zeroDisp() {
        for (Node n1 : graph.getNodes()) {
            n1.getDisp().zero();
        }
    }

    void moveVertexes() {
        for (Node n : graph.getNodes()) {
            Pt delta = n.getDisp().minus(n.getPos());
            double dl = delta.lenSafe();
            n.getPos().incBy(delta.div(dl).mult(min(dl, temp)));
            n.getPos().x = min(req.x / 2d, max(-req.x / 2d, n.getPos().x));
            n.getPos().y = min(req.y / 2d, max(-req.y / 2d, n.getPos().y));
        }
    }

    void reduceTemperature(int iterCurrent, int iterMax) {
        temp *= (1d - (double) iterCurrent / iterMax);
    }

    void repulsion() {
        for (Node n1 : graph.getNodes()) {
            for (Node n2 : graph.getNodes()) {
                if (n1 != n2) {
                    Pt delta = getDelta(n1, n2);
                    double dl = delta.lenSafe();
                    n1.getDisp().incBy(delta.div(dl).mult(repulsionForce(dl)));
                }
            }
        }
    }

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

}
