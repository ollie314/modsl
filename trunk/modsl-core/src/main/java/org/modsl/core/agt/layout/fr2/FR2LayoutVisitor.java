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

import java.util.Map;

import org.apache.log4j.Logger;
import org.modsl.core.agt.model.AbstractBox;
import org.modsl.core.agt.model.Edge;
import org.modsl.core.agt.model.Graph;
import org.modsl.core.agt.model.MetaType;
import org.modsl.core.agt.model.Node;
import org.modsl.core.agt.model.Pt;
import org.modsl.core.agt.visitor.AbstractLayoutVisitor;

/**
 * Modified Fruchterman-Rheingold layout algorithm
 * @author avishnyakov
 */
public class FR2LayoutVisitor extends AbstractLayoutVisitor {

    @SuppressWarnings("unused")
    private Logger log = Logger.getLogger(this.getClass());

    protected double temp, kForce, kAttraction, kRepulsion;

    protected double tempMultiplier, attractionMultiplier, repulsionMultiplier;

    protected int maxIterations;
    protected Graph graph;

    public FR2LayoutVisitor(MetaType type) {
        super(type);
    }

    void attraction() {
        for (Edge e : graph.getEdges()) {
            Pt delta = getDelta(e.getNode1(), e.getNode2());
            double dl = delta.lenSafe();
            e.getNode1().getAltPos().decBy(delta.div(dl).mult(attractionForce(dl)));
            e.getNode2().getAltPos().incBy(delta.div(dl).mult(attractionForce(dl)));
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
        //Pt delta2 = n1.getCtrPos().minus(n2.getCtrPos());
        //log.debug(delta + " " + delta2);
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

        graph.randomize(graph.getName().hashCode());
        graph.recalcSize();
        temp = max((graph.getReqSize().x + graph.getReqSize().y) * tempMultiplier, Pt.EPSILON);
        kForce = max(sqrt(graph.getReqSize().x * graph.getReqSize().y / graph.getNodes().size()), Pt.EPSILON);
        kAttraction = attractionMultiplier * kForce;
        kRepulsion = repulsionMultiplier * kForce;

        for (int iterCurrent = 0; iterCurrent < maxIterations; iterCurrent++) {
            repulsion();
            attraction();
            moveVertexes();
            reduceTemperature(iterCurrent, maxIterations);
        }

    }

    void moveVertexes() {
        Pt req = graph.getReqSize();
        for (Node n : graph.getNodes()) {
            Pt delta = n.getAltPos().minus(n.getPos());
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
            n1.getAltPos().zero();
            for (Node n2 : graph.getNodes()) {
                if (n1 != n2) {
                    Pt delta = getDelta(n1, n2);
                    double dl = delta.lenSafe();
                    n1.getAltPos().incBy(delta.div(dl).mult(repulsionForce(dl)));
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
