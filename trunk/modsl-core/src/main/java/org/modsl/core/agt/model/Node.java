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

package org.modsl.core.agt.model;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.modsl.core.agt.visitor.AbstractVisitor;

/**
 * Graph node
 * @author AVishnyakov
 * @param <T> type enum
 */
public class Node<T extends MetaType> extends AbstractGraphElement<T> {

	/**
	 * List of children nodes
	 */
	protected List<Node<T>> nodes = new LinkedList<Node<T>>();

	/**
	 * Map of children nodes {name->node}
	 */
	protected Map<String, Node<T>> nodeMap = new HashMap<String, Node<T>>();

	/**
	 * List of children edges
	 */
	protected List<Edge<T>> edges = new LinkedList<Edge<T>>();

	/**
	 * Map of children edges {name->edge}
	 */
	protected Map<String, Edge<T>> edgeMap = new HashMap<String, Edge<T>>();

	/**
	 * This element's size
	 */
	protected Pt size = new Pt();

	/**
	 * Alternate position (laceholder for layout algorithms)
	 */
	protected Pt altPos = new Pt();

	/**
	 * This element's requested size
	 */
	protected Pt reqSize = new Pt();

	/**
	 * This element's position (it's left top corner) relative to it's parent
	 * node
	 */
	protected Pt pos = new Pt();

	/**
	 * Top padding
	 */
	protected double padTop = 0d;

	/**
	 * Bottom padding
	 */
	protected double padBottom;

	/**
	 * Left and right padding
	 */
	protected double padSide;

	/**
	 * Create new
	 * @param type type
	 */
	public Node(T type) {
		super(type);
	}

	/**
	 * Create new
	 * @param type type
	 * @param name name
	 */
	public Node(T type, String name) {
		super(type, name);
	}

	@Override
	public void accept(AbstractVisitor<T> visitor) {
		visitor.in(this);
		for (Edge<T> e : edges) {
			e.accept(visitor);
		}
		for (Node<T> n : nodes) {
			n.accept(visitor);
		}
		visitor.out(this);
	}

	/**
	 * Add child edge
	 * @param child
	 */
	public void add(Edge<T> child) {
		child.parent = this;
		edges.add(child);
		edgeMap.put(child.getName(), child);
	}

	/**
	 * Add child node
	 * @param child
	 */
	public void add(Node<T> child) {
		child.parent = this;
		nodes.add(child);
		nodeMap.put(child.getName(), child);
	}

	/**
	 * @return alternate position
	 */
	public Pt getAltPos() {
		return altPos;
	}

	/**
	 * @return node's area in pixels^2
	 */
	public double getArea() {
		return size.x * size.y;
	}

	/**
	 * @return center position, taking node's size into account
	 */
	public Pt getCtrPos() {
		return pos.plus(size.div(2d));
	}

	/**
	 * @param key
	 * @return edge by it's name
	 */
	public Edge<T> getEdge(String key) {
		return edgeMap.get(key);
	}

	/**
	 * @return children edge list
	 */
	public List<Edge<T>> getEdges() {
		return edges;
	}

	/**
	 * Bottom right corner's (x, y)
	 * @return max (x, y)
	 */
	public Pt getMaxPt() {
		Pt s = new Pt(0d, 0d);
		for (Node<T> n : nodes) {
			s.x = max(s.x, n.pos.x + n.size.x);
			s.y = max(s.y, n.pos.y + n.size.y);
		}
		return s;
	}

	/**
	 * Top left corner's (x, y). Can be different from 0, 0 depending on the
	 * nodes' positions
	 * @return min (x, y)
	 */
	public Pt getMinPt() {
		Pt s = new Pt(Double.MAX_VALUE, Double.MAX_VALUE);
		for (Node<T> n : nodes) {
			s.x = min(s.x, n.pos.x);
			s.y = min(s.y, n.pos.y);
		}
		return s;
	}

	/**
	 * @param index
	 * @return node by index
	 */
	public Node<T> getNode(int index) {
		return nodes.get(index);
	}

	/**
	 * @param index
	 * @return edge by index
	 */
	public Edge<T> getEdge(int index) {
		return edges.get(index);
	}

	/**
	 * @param key
	 * @return node by it's name
	 */
	public Node<T> getNode(String key) {
		return nodeMap.get(key);
	}

	/**
	 * @return children node list
	 */
	public List<Node<T>> getNodes() {
		return nodes;
	}

	/**
	 * @return position (top left corner)
	 */
	public Pt getPos() {
		return pos;
	}

	/**
	 * @return size requested by the client
	 */
	public Pt getReqSize() {
		return reqSize;
	}

	/**
	 * @return size
	 */
	public Pt getSize() {
		return size;
	}

	/**
	 * @return sum of all edge lengths
	 */
	public double getSumEdgeLengths() {
		double len = 0d;
		for (Edge<T> e : edges) {
			len += e.getLength();
		}
		return len;
	}

	/**
	 * @return position for single line text (it's bottom left corner), with
	 * font top and left padding taken into account
	 */
	public Pt getTextPos() {
		FontTransform ft = type.getConfig().getFontTransform();
		return new Pt(pos.x + ft.getLeftPadding(), pos.y + ft.getExtBaseline(0));
	}

	/**
	 * @return node with max x (the rightmost one, including it's size)
	 */
	public Node<T> maxXNode() {
		Node<T> res = null;
		for (Node<T> n : nodes) {
			res = res == null ? n : (res.pos.x + res.size.x < n.pos.x + n.size.x ? n : res);
		}
		return res;
	}

	/**
	 * @return node with max y (the lowest one, including it's size)
	 */
	public Node<T> maxYNode() {
		Node<T> res = null;
		for (Node<T> n : nodes) {
			res = res == null ? n : (res.pos.y + res.size.y < n.pos.y + n.size.y ? n : res);
		}
		return res;
	}

	/**
	 * Shift (x, y) on all vertexes to bring min(x, y) to (0, 0)
	 */
	public void normalize() {
		Pt min = getMinPt();
		for (Node<T> n : nodes) {
			n.pos.decBy(min);
		}
	}

	/**
	 * Recalculates and sets size of this (non-normalized) graph to true size of
	 * the non-normalized graph
	 */
	public void recalcSize() {
		size = getMaxPt().minus(getMinPt());
	}

	/**
	 * Rescale diagram to the given size
	 * @param newSize new size
	 */
	public void rescale(Pt newSize) {
		normalize();
		recalcSize();
		Node<T> maxXNode = maxXNode();
		Node<T> maxYNode = maxYNode();
		Pt maxXYSize = new Pt(maxXNode.size.x, maxYNode.size.y);
		Pt newSizeExt = newSize.minus(maxXYSize).decBy(new Pt(padSide * 2d, padTop + padBottom));
		Pt sizeExt = size.minus(maxXYSize);
		Pt topLeft = new Pt(padSide, padTop);
		for (Node<T> n : nodes) {
			n.pos.mulBy(newSizeExt).divBy(sizeExt).incBy(topLeft);
		}
		size = new Pt(newSize);
	}

	/**
	 * Set alternate position
	 * @param altPos
	 */
	public void setAltPos(Pt altPos) {
		this.altPos = altPos;
	}

	/**
	 * Set node's position (top left corner)
	 * @param pos
	 */
	public void setPos(Pt pos) {
		this.pos = pos;
	}

	/**
	 * @param reqSize size requested by client (if any)
	 */
	public void setReqSize(Pt reqSize) {
		this.reqSize = reqSize;
	}

	/**
	 * Set size
	 * @param size
	 */
	public void setSize(Pt size) {
		this.size = size;
	}

	/**
	 * @return sin of angle between 0 and diagonal
	 */
	public double sin() {
		return size.y / size.len();
	}

	/**
	 * @return tan of angle between 0 and diagonal
	 */
	public double tan() {
		return size.y / size.x;
	}

	@Override
	public String toString() {
		return name + ":" + type;
	}

}
