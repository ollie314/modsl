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

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.acos;
import static java.lang.Math.signum;

import org.modsl.core.agt.visitor.AbstractVisitor;

/**
 * Graph edge.
 * 
 * @param T type enum
 * @author avishnyakov
 */
public class Edge<T extends MetaType> extends AbstractGraphElement<T> {

	/**
	 * Start node
	 */
	protected Node<T> node1;

	/**
	 * End node
	 */
	protected Node<T> node2;

	/**
	 * Start node name (will be resolved to node1 reference when post-processing
	 */
	protected String node1Name;

	/**
	 * End node name (will be resolved to node2 reference when post-processing
	 */
	protected String node2Name;

	/**
	 * Are start and end nodes swapped? Needed for the layout algorithms.
	 */
	protected boolean reverted = false;

	/**
	 * Create new
	 * @param type type
	 * @param node1 start node
	 * @param node2 end node
	 */
	public Edge(T type, Node<T> node1, Node<T> node2) {
		super(type);
		this.node1 = node1;
		this.node1.addConnectedEdge(this);
		this.node2 = node2;
		this.node2.addConnectedEdge(this);
	}

	/**
	 * Create new
	 * @param type type
	 * @param name name
	 * @param node1 start node
	 * @param node2 end node
	 */
	public Edge(T type, String name, Node<T> node1, Node<T> node2) {
		this(type, node1, node2);
		this.name = name;
	}

	/**
	 * Create new
	 * @param type type
	 * @param name name
	 * @param node1 start node
	 * @param node2 end node
	 * @param dummy
	 */
	public Edge(T type, String name, Node<T> node1, Node<T> node2, boolean dummy) {
		this(type, name, node1, node2);
		this.dummy = dummy;
	}

	@Override
	public void accept(AbstractVisitor<T> visitor) {
		visitor.in(this);
		visitor.out(this);
	}

	public double angle() {
		Pt delta = getDelta();
		if (delta.y > 0d) {
			return acos(this.cos());
		} else if (delta.y < 0d) {
			return 2 * PI - acos(this.cos());
		} else {
			if (delta.x >= 0d) {
				return 0;
			} else {
				return PI;
			}
		}
	}

	public double cos() {
		Pt delta = getDelta();
		return delta.x / getDelta().len();
	}

	/**
	 * @return (delta(x), delta(y)) between nodes' center positions
	 */
	public Pt getDelta() {
		return node2.getCtrPos().minus(node1.getCtrPos());
	}

	/**
	 * @return length of this edge w/o adjustment for overlay
	 */
	public double getLength() {
		return node2.getPos().minus(node1.getPos()).len();
	}

	/**
	 * @return start node
	 */
	public Node<T> getNode1() {
		return node1;
	}

	/**
	 * @return startpoint position at node 1
	 */
	public Pt getNode1Clip() {
		return getNodeClip(node1, true);
	}

	/**
	 * @return start node name
	 */
	public String getNode1Name() {
		return node1Name;
	}

	/**
	 * @return end node
	 */
	public Node<T> getNode2() {
		return node2;
	}

	/**
	 * @return endpoint position at node 2
	 */
	public Pt getNode2Clip() {
		return getNodeClip(node2, false);
	}

	/**
	 * @return end node name
	 */
	public String getNode2Name() {
		return node2Name;
	}

	/**
	 * Convenience method to calculate adjusted position of the connector's
	 * endpoint at the given node, considering that element's dimensions
	 * @param n node
	 * @param start when connector is directional, set to true if it starts at
	 * <code>n</code>
	 * @return position adjusted to node's size
	 */
	protected Pt getNodeClip(Node<T> n, boolean start) {
		Pt ap = new Pt();
		Pt cp = n.getCtrPos();
		Pt s = n.getSize();
		double sign = start ? 1d : -1d;
		if (abs(n.tan()) > abs(tan())) {
			// i.e. line is crossing e2's side
			ap.x = cp.x + sign * s.x * signum(cos()) / 2d;
			ap.y = cp.y + sign * s.x * tan() * signum(cos()) / 2d;
		} else {
			ap.x = cp.x + sign * s.y / tan() * signum(sin()) / 2d;
			ap.y = cp.y + sign * s.y * signum(sin()) / 2d;
		}
		return ap;
	}

	public boolean isReverted() {
		return reverted;
	}

	/**
	 * Set start node
	 * @param n1 start node
	 */
	public void setNode1(Node<T> n1) {
		if (node1 != null && !node1.equals(n1)) {
			node1.removeConnectedEdge(this);
			node1 = n1;
			node1.addConnectedEdge(this);
		}
	}

	/**
	 * Set end node
	 * @param n2 end node
	 */
	public void setNode2(Node<T> n2) {
		if (node2 != null && !node2.equals(n2)) {
			node2.removeConnectedEdge(this);
			node2 = n2;
			node2.addConnectedEdge(this);
		}
	}

	public void setReverted(boolean r) {
		if (reverted != r) {
			Node<T> tn = node1;
			node1 = node2;
			node2 = tn;
			String tnn = node1Name;
			node1Name = node2Name;
			node2Name = tnn;
			reverted = r;
		}
	}

	public double sin() {
		Pt delta = getDelta();
		return delta.y / getDelta().len();
	}

	public double tan() {
		Pt delta = getDelta();
		return delta.y / delta.x;
	}

	@Override
	public String toString() {
		return name + ":" + type + "(" + (node1 == null ? "*" + node1Name : node1.getName()) + "->"
				+ (node2 == null ? "*" + node2Name : node2.getName()) + ")";
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Edge) {
			Edge e = (Edge) obj;
			if (this == e) {
				return true;
			} else {
				boolean same = true;
				if (node1 == null) {
					if (e.node1 == null) {
						same = same && node1Name.equals(e.node1Name);
					} else {
						same = false;
					}
				} else {
					same = same && node1.equals(e.node1);
				}
				if (node2 == null) {
					if (e.node2 == null) {
						same = same && node2Name.equals(e.node2Name);
					} else {
						same = false;
					}
				} else {
					same = same && node2.equals(e.node2);
				}
				return same;
			}
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (node1 == null ? 0 : node1.hashCode()) + (node2 == null ? 0 : node2.hashCode());
	}

}