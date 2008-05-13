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
			Pt delta = e.getNode1Port().minus(e.getNode2Port());
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

	Pt getDelta(Node n1, Node n2) {
		Pt delta = n1.getCtrPos().minus(n2.getCtrPos());
		// String s = delta.toString();
		/*
		 * if (n1.getSize().y > n1.getSize().x) { delta.decBy((n1.getSize().y -
		 * n1.getSize().x) / 2d); } if (n2.getSize().y > n2.getSize().x) {
		 * delta.decBy((n2.getSize().y - n2.getSize().x) / 2d); }
		 */
		// TODO bigger element adjustment
		// delta.decBy(v1.getDiagonal() / 8d);
		// delta.decBy(v2.getDiagonal() / 8d);
		if (delta.len() < Pt.EPSILON) {
			delta.randomize(Pt.EPSILON);
		}
		// log.debug(s + ":" + delta);
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
		for (Node n : graph.getNodes()) {
			Pt delta = n.getAltPos().minus(n.getPos());
			double dl = delta.lenSafe();
			n.getPos().incBy(delta.div(dl).mult(min(dl, temp)));
			n.getPos().x = min(graph.getReqSize().x / 2d, max(-graph.getReqSize().x / 2d, n.getPos().x));
			n.getPos().y = min(graph.getReqSize().y / 2d, max(-graph.getReqSize().y / 2d, n.getPos().y));
		}
	}

	void reduceTemperature(int iterCurrent, int iterMax) {
		temp *= (1d - (double) iterCurrent / iterMax);
	}

	void repulsion() {
		for (Node na : graph.getNodes()) {
			na.getAltPos().zero();
			for (Node nb : graph.getNodes()) {
				if (na != nb) {
					Pt delta = getDelta(na, nb);
					double dl = delta.lenSafe();
					na.getAltPos().incBy(delta.div(dl).mult(repulsionForce(dl)));
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
