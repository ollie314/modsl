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

package org.modsl.cls.layout;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.floor;
import static java.lang.Math.random;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

import org.apache.log4j.Logger;
import org.modsl.cls.ClassDiagramConfig;
import org.modsl.cls.model.ClassDiagram;
import org.modsl.cls.model.ClassElement;
import org.modsl.core.layout.AbstractLayout;
import org.modsl.core.model.XY;

public class InitialCirclePosition extends AbstractLayout<ClassDiagram, ClassDiagramConfig> {

	private Logger log = Logger.getLogger(getClass());

	protected double angle;
	protected ClassDiagram diag;
	protected int circlePositions;
	protected double initLayoutWeightSegment = 1d / 3d;

	public InitialCirclePosition(ClassDiagramConfig config) {
		super(config);
	}

	public void apply(ClassDiagram diag) {

		this.diag = diag;
		this.circlePositions = diag.getElements().size();
		this.angle = 2d * PI / circlePositions;

		diag.calcElementWeights();

		initCircle();
		optimizeEdgeLength();
		// rotateToMinWeight();

	}

	private void rotateToMinWeight() {
		int minWIndex = findMinWeightSegment();
		// index points to the left boundary of the min weight segment
		// need to be brought to the SOUTH - 1/6 point
		int shiftBy = circlePositions - minWIndex - (int) (initLayoutWeightSegment * circlePositions / 2d);
		for (int i = 0; i < circlePositions; i++) {
			int newp = i - shiftBy;
			newp = newp < 0 ? newp + circlePositions : newp;
			setCirclePosition(diag.getElement(i), newp);
		}
	}

	private int findMinWeightSegment() {
		double minW = Double.MAX_VALUE;
		int minWp = -1;
		for (int i = 0; i < circlePositions; i++) {
			double cw = calcSegmentWeight(i);
			if (cw < minW) {
				minW = cw;
				minWp = i;
			}
		}
		return minWp;
	}

	/**
	 * Calculates weight for the circle segment between p1 to p1+1/3 of the
	 * circle
	 * 
	 * @param i
	 */
	private double calcSegmentWeight(int p1) {
		int p2 = p1 + (int) (initLayoutWeightSegment * circlePositions);
		double w = 0d;
		for (int j = p1; j < p2; j++) {
			if (j < circlePositions) {
				w += diag.getElement(j).getWeight();
			} else {
				w += diag.getElement(j - circlePositions).getWeight();
			}
		}
		return w;
	}

	private void optimizeEdgeLength() {
		circlePositions = diag.getElements().size();
		for (int i = 0; i < config.initLayoutMaxRounds * circlePositions; i++) {
			double curLen = diag.getSumEdgeLength();
			int p1 = (int) floor(random() * circlePositions);
			int p2 = (int) floor(random() * circlePositions);
			swap(p1, p2);
			if (diag.getSumEdgeLength() > curLen) {
				swap(p1, p2);
			}
		}
	}

	private void initCircle() {
		for (int iv = 0; iv < diag.getElements().size(); iv++) {
			setCirclePosition(diag.getElement(iv), iv);
		}
	}

	private void setCirclePosition(ClassElement e, int p) {
		e.getPosition().x = getRequestedSizeDiagonal() / 2d * cos(getPositionAngle(p));
		e.getPosition().y = getRequestedSizeDiagonal() / 2d * sin(getPositionAngle(p));
	}

	private double getRequestedSizeDiagonal() {
		return sqrt(diag.getRequestedSize().x * diag.getRequestedSize().x + diag.getRequestedSize().y
				* diag.getRequestedSize().y);
	}

	private double getPositionAngle(int p) {
		return PI / 4d - angle * p;
	}

	private void swap(int p1, int p2) {
		ClassElement e1 = diag.getElement(p1);
		ClassElement e2 = diag.getElement(p2);
		XY xy1 = e1.getPosition();
		e1.setPosition(e2.getPosition());
		e2.setPosition(xy1);
	}
}
