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

package OBSOLETE.org.modsl.core.model.graph;

import java.util.Collection;
import java.util.List;

import OBSOLETE.org.modsl.core.model.XY;

/**
 * Represents a graph for the layout algorithm
 * 
 * @author avishnyakov
 */
public interface Graph {

	/**
	 * Adds current position of each vertex to its local history
	 */
	public void addPositionToHistory();

	/**
	 * @return area of the graph (width * height)
	 */
	public double getArea();

	/**
	 * @return baricenter of the graph
	 */
	public XY getBaricenter();

	/**
	 * @return list of edge lengths
	 */
	public List<Double> getEdgeLengths();

	/**
	 * @return list of all edges
	 */
	public List<Edge> getEdges();

	/**
	 * @return size (width, height) of this graph
	 */
	public XY getSize();

	/**
	 * @return sum of all edge lengths in the graph
	 */
	public double getSumEdgeLength();

	/**
	 * @param i
	 * @return vertex for given index i
	 */
	public Vertex getVertex(int i);

	/**
	 * @return list of all vertexes
	 */
	public Collection<Vertex> getVertexes();

	/**
	 * @return recalculate size based on max(x,y) across all vertexes
	 */
	public XY recalcSize();

}
