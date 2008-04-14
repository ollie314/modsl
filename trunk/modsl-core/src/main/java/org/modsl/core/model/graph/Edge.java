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

package org.modsl.core.model.graph;

import org.modsl.core.model.XY;

/**
 * Represents an edge of a graph for the layout algorithm
 * 
 * @author avishnyakov
 * 
 */
public interface Edge {

	/**
	 * @return start vertex
	 */
	public Vertex getStartVertex();

	/**
	 * @return end vertex
	 */
	public Vertex getEndVertex();

	/**
	 * @return length of this edge between centers of the vertexes
	 */
	public double getLength();

	/**
	 * @return tan() of the angle between this edge and horizontal line at the
	 *         start vertex
	 */
	public double tan();

	/**
	 * @return sin() of the angle between this edge and horizontal line at the
	 *         start vertex
	 */
	public double sin();

	/**
	 * @return cos() of the angle between this edge and horizontal line at the
	 *         start vertex
	 */
	public double cos();
	
	/**
	 * @return angle from 0 to 2*PI
	 */
	public double angle();

	/**
	 * @return start XY adjusted for vertex size
	 */
	public XY getAdjustedStartPosition();

	/**
	 * @return end XY adjusted for vertex size
	 */
	public XY getAdjustedEndPosition();

}
