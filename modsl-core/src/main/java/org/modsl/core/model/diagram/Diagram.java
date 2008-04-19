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

package org.modsl.core.model.diagram;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.modsl.core.common.Timeline;
import org.modsl.core.model.XY;
import org.modsl.core.model.graph.Graph;
import org.modsl.core.model.graph.Vertex;

/**
 * Basic diagram
 * 
 * @author avishnyakov
 * 
 * @param
 * <P>
 * parent class (usually just Object for the diagram, since no parent is needed)
 * @param <E>
 *            connector class
 * @param <C>
 *            element class
 */
public class Diagram<P, E extends Element<?, ?>, C extends Connector<?, ?>> extends AbstractDiagramObject<P> implements Graph {

	/**
	 * Unordered elements for easy lookup by name
	 */
	protected Map<String, E> elements = new HashMap<String, E>();

	/**
	 * Ordered elements for indexed access
	 */
	protected List<E> orderedElements = new ArrayList<E>();

	/**
	 * Connectors (allows indexed access)
	 */
	protected List<C> connectors = new LinkedList<C>();

	protected XY size = new XY();
	protected XY lastKnownSize = new XY();
	protected XY requestedSize = new XY();

	// paddings
	protected double paddingHeader;
	protected double paddingFooter;
	protected double paddingSides;

	protected Timeline timeline = new Timeline();
	
	protected String output;

	public Diagram(P parent, String name) {
		super(parent, name);
	}

	public void addConnector(C connector) {
		connectors.add(connector);
	}

	public void addElement(E element) {
		elements.put(element.getName(), element);
		orderedElements.add(element);
	}

	public void addPositionToHistory() {
		for (Vertex v : orderedElements) {
			v.addPositionToHistory();
		}
	}

	public double getArea() {
		return size.x * size.y;
	}

	public XY getBaricenter() {
		XY bc = new XY();
		for (E e : orderedElements) {
			bc.x += e.getCenterPosition().x;
			bc.y += e.getCenterPosition().y;
		}
		bc.divBy(orderedElements.size());
		return bc;
	}

	public List<C> getConnectors() {
		return connectors;
	}

	public List<Double> getEdgeLengths() {
		List<Double> lens = new ArrayList<Double>(connectors.size());
		for (C e : connectors) {
			lens.add(e.getLength());
		}
		return lens;
	}

	public List getEdges() {
		return connectors;
	}

	public E getElement(int i) {
		return orderedElements.get(i);
	}

	public E getElement(String key) {
		return elements.get(key);
	}

	public List<E> getElements() {
		return orderedElements;
	}

	public XY getLastKnownSize() {
		return lastKnownSize;
	}

	/**
	 * (x, y) for bottom right
	 * 
	 * @return
	 */
	public XY getMaxXY() {
		XY s = new XY();
		for (Vertex v : elements.values()) {
			s.x = max(s.x, v.getPosition().x + v.getSize().x);
			s.y = max(s.y, v.getPosition().y + +v.getSize().y);
		}
		return s;
	}

	/**
	 * (x, y) for top left. Can differ from 0, 0 depending on the vertexes'
	 * positions
	 * 
	 * @return
	 */
	public XY getMinXY() {
		XY s = new XY(Double.MAX_VALUE, Double.MAX_VALUE);
		for (Vertex v : elements.values()) {
			s.x = min(s.x, v.getPosition().x);
			s.y = min(s.y, v.getPosition().y);
		}
		return s;
	}

	public double getPaddingFooter() {
		return paddingFooter;
	}

	public double getPaddingHeader() {
		return paddingHeader;
	}

	public double getPaddingSides() {
		return paddingSides;
	}

	public XY getRequestedSize() {
		return requestedSize;
	}

	public XY getSize() {
		return size;
	}

	public double getSumEdgeLength() {
		double len = 0d;
		for (C e : connectors) {
			len += e.getLength();
		}
		return len;
	}

	public Timeline getTimeline() {
		return timeline;
	}

	public E getVertex(int i) {
		return orderedElements.get(i);
	}

	public List getVertexes() {
		return orderedElements;
	}

	/**
	 * @return vertex with max x (the rightmost, including it's size)
	 */
	protected Vertex maxXVertex() {
		Vertex res = null;
		for (Vertex v : orderedElements) {
			res = res == null ? v : (res.getPosition().x + res.getSize().x < v.getPosition().x + v.getSize().x ? v : res);
		}
		return res;
	}

	/**
	 * @return vertex with max y (the lowest, including it's size)
	 */
	protected Vertex maxYVertex() {
		Vertex res = null;
		for (Vertex v : orderedElements) {
			res = res == null ? v : (res.getPosition().y + res.getSize().y < v.getPosition().y + v.getSize().y ? v : res);
		}
		return res;
	}

	/**
	 * Shift x, y on all vertexes to bring min(x, y) to (0, 0)
	 */
	public void rebase() {
		XY min = getMinXY();
		for (Vertex v : elements.values()) {
			v.getPosition().decBy(min);
			for (XY hist : v.getPosHistory()) {
				hist.decBy(min);
			}
		}
	}

	public XY recalcSize() {
		size = getMaxXY().minus(getMinXY());
		return size;
	}

	/**
	 * Rescale diagram to the given size
	 * 
	 * @param newSize
	 *            new size
	 */
	protected void rescale(XY newSize) {
		rebase();
		recalcSize();
		lastKnownSize = size;
		Vertex maxX = maxXVertex();
		Vertex maxY = maxYVertex();
		XY maxXYsize = new XY(maxX.getSize().x, maxY.getSize().y);
		XY newSizeExt = newSize.minus(maxXYsize).decBy(new XY(paddingSides * 2, paddingHeader + paddingFooter));
		XY sizeExt = size.minus(maxXYsize);
		for (Vertex v : elements.values()) {
			XY pos = v.getPosition();
			pos.mulBy(newSizeExt).divBy(sizeExt).incBy(new XY(paddingSides, paddingHeader));
			for (XY hist : v.getPosHistory()) {
				hist.mulBy(newSizeExt).divBy(sizeExt).incBy(new XY(paddingSides, paddingHeader));
			}
		}
		size = new XY(newSize);
	}

	public void rescaleToRequestedSize() {
		rescale(getRequestedSize());
	}

	public void setPaddingFooter(double paddingFooter) {
		this.paddingFooter = paddingFooter;
	}

	public void setPaddingHeader(double paddingHeader) {
		this.paddingHeader = paddingHeader;
	}

	public void setPaddingSides(double paddingSides) {
		this.paddingSides = paddingSides;
	}

	public void setRequestedSize(XY requestedSize) {
		this.requestedSize = requestedSize;
	}

	public void timestamp(String name) {
		timeline.timestamp(name);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "~{e:" + orderedElements + ",c:" + connectors + "}";
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

}