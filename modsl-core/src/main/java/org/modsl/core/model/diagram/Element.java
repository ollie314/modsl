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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.modsl.core.model.XY;
import org.modsl.core.model.graph.Vertex;

public class Element<P extends Diagram, ED extends ElementDetail> extends AbstractDiagramObject<P> implements Vertex {

    protected static int sequence = 0;

    protected Map<String, ED> elementDetails = new HashMap<String, ED>();
    protected List<ED> orderedElementDetails = new LinkedList<ED>();
    protected List<XY> history = new LinkedList<XY>();

    protected XY position = new XY(sequence++, sequence++);
    protected XY size = new XY(1d, 1d);
    protected XY headerPosition = new XY();
    protected XY altPos = new XY();
    protected double weight = 1d;

    public Element(P parent, String name) {
        super(parent, name);
        this.parent.addElement(this);
    }

    public void addElementDetail(ED detail) {
        elementDetails.put(detail.getName(), detail);
        orderedElementDetails.add(detail);
    }

    public ED getDetail(String key) {
        return elementDetails.get(key);
    }

    public List<ED> getDetails() {
        return orderedElementDetails;
    }

    public XY getSize() {
        return size;
    }

    public XY getPosition() {
        return position;
    }

    public void setPosition(XY position) {
        this.position = position;
    }

    public XY getCenterPosition() {
        return position.plus(size.div(2d));
    }

    public XY getAltPosition() {
        return altPos;
    }

    public List<XY> getPosHistory() {
        return history;
    }

    public void addPositionToHistory() {
        history.add(new XY(getPosition()));
    }

    public String toString() {
        return "{" + getName() + "," + getPosition() + "}";
    }

    public double tan() {
        return size.y / size.x;
    }

    public double sin() {
        return size.y / getDiagonal();
    }

    public double getDiagonal() {
        return Math.sqrt(size.x * size.x + size.y * size.y);
    }

    public double cos() {
        return size.x / getDiagonal();
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setSize(XY size) {
        this.size = size;
    }

}