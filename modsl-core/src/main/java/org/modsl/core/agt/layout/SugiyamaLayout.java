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

package org.modsl.core.agt.layout;

import static java.lang.Math.max;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.modsl.core.agt.common.ModSLException;
import org.modsl.core.agt.model.Edge;
import org.modsl.core.agt.model.Node;

public class SugiyamaLayout extends AbstractNonConfigurableLayout {

	protected int dummyCount = 1;

	@Override
	public void apply(Node<?> root) {
		removeCycles(root);
		layer(root);
		insertDummies(root);
		undoRemoveCycles(root);
	}

	void insertDummies(Node<?> root) {
		for (Edge<?> currEdge : root.getChildEdges()) {
			if (currEdge.getNode2().getIndex() - currEdge.getNode1().getIndex() > 1) {
				Edge<?> edgeToSplit = currEdge;
				for (int layer = currEdge.getNode1().getIndex() + 1; layer < currEdge.getNode2().getIndex(); layer++) {
					edgeToSplit = split(edgeToSplit, root);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	Edge<?> split(Edge<?> edge, Node<?> root) {
		Node dummyNode = new Node(edge.getNode2().getType(), "dummyNode" + dummyCount++, true);
		dummyNode.setIndex(edge.getNode2().getIndex() - 1);
		root.add(dummyNode);
		Edge dummyEdge = new Edge(edge.getType(), "dummyEdge" + dummyCount++, dummyNode, edge.getNode2(), true);
		edge.setNode2(dummyNode);
		root.addChild(dummyEdge);
		return dummyEdge;
	}

	void layer(Node<?> root) {
		List<Node<?>> sorted = topologicalSort(root);
		for (Node<?> n : sorted) {
			n.setIndex(1);
		}
		int h = 0;
		for (Node<?> n1 : sorted) {
			for (Edge<?> out : n1.getOutEdges()) {
				Node<?> n2 = out.getNode2();
				n2.setIndex(max(n1.getIndex() + 1, n2.getIndex()));
				h = max(h, n2.getIndex());
			}
		}

	}

	void removeCycles(Node<?> root) {
		List<Node<?>> nodes = sortByOutDegree(root);
		Set<Edge<?>> removed = new HashSet<Edge<?>>(root.getChildEdges().size());
		for (Node<?> n : nodes) {
			for (Edge<?> in : new ArrayList<Edge<?>>(n.getInEdges())) {
				if (!removed.contains(in)) {
					in.setReverted(true);
					removed.add(in);
				}
			}
			for (Edge<?> out : n.getOutEdges()) {
				if (!removed.contains(out)) {
					removed.add(out);
				}
			}
		}
	}

	List<Node<?>> sortByOutDegree(Node<?> root) {
		List<Node<?>> nodes = new ArrayList<Node<?>>(root.getNodes());
		Collections.sort(nodes, new Comparator<Node<?>>() {
			public int compare(Node<?> n1, Node<?> n2) {
				return n2.getOutDegree() - n1.getOutDegree();
			}
		});
		return nodes;
	}

	List<Node<?>> sources(Node<?> root) {
		List<Node<?>> sources = new LinkedList<Node<?>>();
		for (Node<?> n : root.getNodes()) {
			if (n.getInDegree() == 0) {
				sources.add(n);
			}
		}
		return sources;
	}

	List<Node<?>> topologicalSort(Node<?> root) {
		List<Node<?>> q = sources(root);
		List<Node<?>> l = new LinkedList<Node<?>>();
		while (q.size() > 0) {
			Node<?> n = q.remove(0);
			l.add(n);
			for (Edge<?> e : n.getOutEdges()) {
				Node<?> m = e.getNode2();
				boolean allEdgesRemoved = true;
				for (Edge<?> e2 : m.getInEdges()) {
					if (!l.contains(e2.getNode1())) {
						allEdgesRemoved = false;
					}
				}
				if (allEdgesRemoved) {
					q.add(m);
				}
			}
		}
		if (root.getNodes().size() != l.size()) {
			throw new ModSLException("Topological sort failed for " + root + " in Sugiyama layout");
		}
		return l;
	}

	void undoRemoveCycles(Node<?> root) {
		for (Edge<?> e : root.getChildEdges()) {
			e.setReverted(false);
		}
	}

}
