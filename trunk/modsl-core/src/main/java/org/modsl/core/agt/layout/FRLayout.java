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

package org.modsl.core.agt.layout;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.sqrt;

import java.util.Map;

import org.apache.log4j.Logger;
import org.modsl.core.agt.model.Edge;
import org.modsl.core.agt.model.Node;
import org.modsl.core.agt.model.Pt;

/**
 * Fruchterman-Rheingold layout algorithm
 * @author avishnyakov
 */
public class FRLayout implements Layout {

	@SuppressWarnings("unused")
	private Logger log = Logger.getLogger(this.getClass());

	protected double temp, kForce, kAttraction, kRepulsion;

	protected double tempMultiplier, attractionMultiplier, repulsionMultiplier;
	protected int maxIterations;

	protected Node<?> root;

	@Override
	public void apply(Edge<?> edge) {
		// no op
	}

	@Override
	public void apply(Node<?> root) {

		this.root = root;

		root.recalcSize();
		Pt gsize = root.getSize();
		temp = max((gsize.x + gsize.y) * tempMultiplier, Pt.EPSILON);
		kForce = max(sqrt(root.getArea() / root.getNodes().size()), Pt.EPSILON);
		kAttraction = attractionMultiplier * kForce;
		kRepulsion = repulsionMultiplier * kForce;

		for (int iterCurrent = 0; iterCurrent < maxIterations; iterCurrent++) {
			repulsion();
			attraction();
			moveVertexes();
			reduceTemperature(iterCurrent, maxIterations);
		}

	}

	private void attraction() {
		for (Edge<?> e : root.getEdges()) {
			Pt delta = e.getNode1Clip().minus(e.getNode2Clip());
			double dl = delta.lenSafe();
			e.getNode1().getAltPos().decBy(delta.div(dl).mult(attractionForce(dl)));
			e.getNode2().getAltPos().incBy(delta.div(dl).mult(attractionForce(dl)));
		}
	}

	protected double attractionForce(double dist) {
		return dist * dist * dist / kAttraction; // TODO was ^2... ^3 ???
	}

	@Override
	public String getConfigName() {
		return "fr_layout";
	}

	private Pt getDelta(Node<?> n1, Node<?> n2) {
		Pt delta = n1.getCtrPos().minus(n2.getCtrPos());
		// String s = delta.toString();
		if (n1.getSize().y > n1.getSize().x) {
			delta.decBy((n1.getSize().y - n1.getSize().x) / 2d);
		}
		if (n2.getSize().y > n2.getSize().x) {
			delta.decBy((n2.getSize().y - n2.getSize().x) / 2d);
		}
		// TODO bigger element adjustment
		// delta.decBy(v1.getDiagonal() / 8d);
		// delta.decBy(v2.getDiagonal() / 8d);
		if (delta.len() < Pt.EPSILON) {
			delta.randomize(Pt.EPSILON);
		}
		// log.debug(s + ":" + delta);
		return delta;
	}

	private void moveVertexes() {
		for (Node<?> n : root.getNodes()) {
			Pt delta = n.getAltPos().minus(n.getPos());
			double dl = delta.lenSafe();
			n.getPos().incBy(delta.div(dl).mult(min(dl, temp)));
		}
	}

	private void reduceTemperature(int iterCurrent, int iterMax) {
		temp *= (1d - (double) iterCurrent / iterMax);
	}

	private void repulsion() {
		for (Node<?> na : root.getNodes()) {
			na.getAltPos().zero();
			for (Node<?> nb : root.getNodes()) {
				if (na != nb) {
					Pt delta = getDelta(na, nb);
					double dl = delta.lenSafe();
					na.getAltPos().incBy(delta.div(dl).mult(repulsionForce(dl)));
				}
			}
		}
	}

	protected double repulsionForce(double dist) {
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
