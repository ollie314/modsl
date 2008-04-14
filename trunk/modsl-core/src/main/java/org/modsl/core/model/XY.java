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

package org.modsl.core.model;

import static java.lang.Math.abs;
import static java.lang.Math.random;

/**
 * Represents 2d point or 2d vector
 * 
 * @author avishnyakov
 */
public class XY {

	public double x;
	public double y;

	/**
	 * "Epsilon" value
	 */
	public final static double EPSILON = 0.0000001d;

	public XY(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public XY() {
		zero();
	}

	public XY(XY p) {
		this.x = p.x;
		this.y = p.y;
	}

	/**
	 * Adds p to this instance and returns the result as a new instance
	 * 
	 * @param p
	 * @return new instance
	 */
	public XY plus(XY p) {
		return new XY(x + p.x, y + p.y);
	}

	/**
	 * Subtracts parameter from this instance and returns the result as a new
	 * instance
	 * 
	 * @param p
	 * @return new instance
	 */
	public XY minus(XY p) {
		return new XY(x - p.x, y - p.y);
	}

	/**
	 * Multiplies this instance by parameter and returns the result as a new
	 * instance
	 * 
	 * @param p
	 * @return new instance
	 */
	public XY mult(double k) {
		return new XY(k * x, k * y);
	}

	/**
	 * Divides this instance by parameter and returns the result as a new
	 * instance
	 * 
	 * @param p
	 * @return new instance
	 */
	public XY div(double k) {
		return new XY(x / k, y / k);
	}

	/**
	 * Calculates the length of this vector
	 * 
	 * @return
	 */
	public double len() {
		return Math.sqrt(x * x + y * y);
	}

	/**
	 * Calculates safe length, i.e. if length is 0 it will return EPS
	 * 
	 * @return length of XY vector, EPS of length is 0
	 */
	public double lenSafe() {
		return Math.max(len(), EPSILON);
	}

	public String toString() {
		return "(" + (float) x + "," + (float) y + ")";
	}

	/**
	 * Decrements this instance by given value.
	 * 
	 * @param p
	 */
	public XY decBy(XY p) {
		x -= p.x;
		y -= p.y;
		return this;
	}

	/**
	 * Decrements this instance by given value.
	 * 
	 * @param p
	 */
	public XY decBy(double d) {
		x -= d;
		y -= d;
		return this;
	}

	/**
	 * Increments this instance by given value.
	 * 
	 * @param p
	 */
	public XY incBy(double d) {
		x += d;
		y += d;
		return this;
	}

	/**
	 * Increments this instance by given value
	 * 
	 * @param p
	 */
	public XY incBy(XY p) {
		x += p.x;
		y += p.y;
		return this;
	}

	/**
	 * Multiplies this instance by given value.
	 * 
	 * @param p
	 */
	public XY mulBy(XY p) {
		x *= p.x;
		y *= p.y;
		return this;
	}

	/**
	 * Multiplies this instance by given value.
	 * 
	 * @param p
	 */
	public XY mulBy(double d) {
		x *= d;
		y *= d;
		return this;
	}

	/**
	 * Divides this instance by given value.
	 * 
	 * @param p
	 */
	public XY divBy(XY p) {
		x /= p.x;
		y /= p.y;
		return this;
	}

	/**
	 * Divides this instance by given value.
	 * 
	 * @param d
	 */
	public XY divBy(double d) {
		x /= d;
		y /= d;
		return this;
	}

	/**
	 * Zeroes x and y
	 */
	public void zero() {
		x = 0d;
		y = 0d;
	}

	/**
	 * Randomizes x and y within given range
	 * 
	 * @param range
	 */
	public void randomize(double range) {
		x = random() * range;
		y = random() * range;
	}

	/**
	 * @return true if both x and y are less than EPS
	 */
	public boolean isZero() {
		return x < EPSILON && y < EPSILON;
	}

	public boolean equals(Object obj) {
		XY xy = (XY) obj;
		return abs(this.x - xy.x) < EPSILON && abs(this.y - xy.y) < EPSILON;
	}
}
