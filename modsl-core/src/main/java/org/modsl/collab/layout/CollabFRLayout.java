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

package org.modsl.collab.layout;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.sqrt;

import org.apache.log4j.Logger;
import org.modsl.collab.CollabDiagramConfig;
import org.modsl.core.layout.AbstractLayout;
import org.modsl.core.model.XY;
import org.modsl.core.model.graph.Edge;
import org.modsl.core.model.graph.Graph;
import org.modsl.core.model.graph.Vertex;

/**
 * Fruchterman-Rheingold layout algorithm
 * 
 * @author avishnyakov
 */
public class CollabFRLayout extends AbstractLayout<Graph, CollabDiagramConfig> {

	private Logger log = Logger.getLogger(this.getClass());

	protected double temp, kForce, kAttraction, kRepulsion;

	public CollabFRLayout(CollabDiagramConfig config) {
		super(config);
	}

	public void apply(Graph g) {

		g.recalcSize();
		XY gsize = g.getSize();
		temp = max((gsize.x + gsize.y) * config.layoutTempMultiplier, XY.EPSILON);
		kForce = max(sqrt(g.getArea() / g.getVertexes().size()), XY.EPSILON);
		kAttraction = config.layoutAttractionMultiplier * kForce;
		kRepulsion = config.layoutRepulsionMultiplier * kForce;

		g.addPositionToHistory();

		for (int iterCurrent = 0; iterCurrent < config.layoutMaxIterations; iterCurrent++) {
			repulsion(g);
			attraction(g);
			moveVertexes(g);
			reduceTemperature(iterCurrent, config.layoutMaxIterations);
			g.addPositionToHistory();
			//g.recalcSize();
		}

	}

	private void repulsion(Graph g) {
		for (Vertex v : g.getVertexes()) {
			v.getAltPosition().zero();
			for (Vertex u : g.getVertexes()) {
				if (v != u) {
					XY delta = getDelta(v, u);
					double dl = delta.lenSafe();
					v.getAltPosition().incBy(delta.div(dl).mult(repulsionForce(dl)));
				}
			}
		}
	}

	private XY getDelta(Vertex v1, Vertex v2) {
		XY delta = v1.getCenterPosition().minus(v2.getCenterPosition());
		// String s = delta.toString();
		if (v1.getSize().y > v1.getSize().x) {
			delta.decBy((v1.getSize().y - v1.getSize().x) / 2d);
		}
		if (v2.getSize().y > v2.getSize().x) {
			delta.decBy((v2.getSize().y - v2.getSize().x) / 2d);
		}
		// TODO bigger element adjustment
		// delta.decBy(v1.getDiagonal() / 8d);
		// delta.decBy(v2.getDiagonal() / 8d);
		if (delta.len() < XY.EPSILON) {
			delta.randomize(XY.EPSILON);
		}
		// log.debug(s + ":" + delta);
		return delta;
	}

	private void attraction(Graph g) {
		for (Edge e : g.getEdges()) {
			// XY delta =
			// e.getStartVertex().getPosition().minus(e.getEndVertex().getPosition());
			// try to use size adjusted position
			XY delta = e.getAdjustedStartPosition().minus(e.getAdjustedEndPosition());
			double dl = delta.lenSafe();
			e.getStartVertex().getAltPosition().decBy(delta.div(dl).mult(attractionForce(dl)));
			e.getEndVertex().getAltPosition().incBy(delta.div(dl).mult(attractionForce(dl)));
		}
	}

	private void moveVertexes(Graph g) {
		for (Vertex v : g.getVertexes()) {
			XY delta = v.getAltPosition().minus(v.getPosition());
			double dl = delta.lenSafe();
			v.getPosition().incBy(delta.div(dl).mult(min(dl, temp)));
		}
	}

	private void reduceTemperature(int iterCurrent, int iterMax) {
		temp *= (1d - (double) iterCurrent / iterMax);
	}

	protected double repulsionForce(double dist) {
		return kRepulsion * kRepulsion / dist;
	}

	protected double attractionForce(double dist) {
		return dist * dist / kAttraction;
	}

}
