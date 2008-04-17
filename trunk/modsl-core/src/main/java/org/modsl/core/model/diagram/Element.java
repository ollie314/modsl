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

/**
 * Basic diagram element (box with name and size)
 * @author avishnyakov
 *
 * @param <P> parent class (usually a Diagram)
 * @param <ED> element detail class or Object if none
 */
public class Element<P extends Diagram, ED extends ElementDetail> extends AbstractDiagramObject<P> implements Vertex {

    protected static int sequence = 0;

    /**
     * All details for easy lookup by name
     */
    protected Map<String, ED> elementDetails = new HashMap<String, ED>();
    
    /**
     * Ordered list of details for indexed lookup
     */
    protected List<ED> orderedElementDetails = new LinkedList<ED>();
    
    /**
     * Position history
     */
    protected List<XY> history = new LinkedList<XY>();

    /**
     * Current position
     */
    protected XY position = new XY(sequence++, sequence++);
    
    /**
     * Size
     */
    protected XY size = new XY(1d, 1d);
    
    /**
     * New/alternate position (for layout calculation)
     */
    protected XY altPos = new XY();
    
    /**
     * "Weight"
     */
    protected double weight = 1d;

    /**
     * New instance given parent diagram and name 
     * @param parent
     * @param name
     */
    public Element(P parent, String name) {
        super(parent, name);
        this.parent.addElement(this);
    }

    public void addElementDetail(ED detail) {
        elementDetails.put(detail.getName(), detail);
        orderedElementDetails.add(detail);
    }

    public void addPositionToHistory() {
        history.add(new XY(getPosition()));
    }

    public double cos() {
        return size.x / getDiagonal();
    }

    public XY getAltPosition() {
        return altPos;
    }

    public XY getCenterPosition() {
        return position.plus(size.div(2d));
    }

    public ED getDetail(String key) {
        return elementDetails.get(key);
    }

    public List<ED> getDetails() {
        return orderedElementDetails;
    }

    public double getDiagonal() {
        return Math.sqrt(size.x * size.x + size.y * size.y);
    }

    public List<XY> getPosHistory() {
        return history;
    }

    public XY getPosition() {
        return position;
    }

    public XY getSize() {
        return size;
    }

    public double getWeight() {
        return weight;
    }

    public void setPosition(XY position) {
        this.position = position;
    }

    public void setSize(XY size) {
        this.size = size;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double sin() {
        return size.y / getDiagonal();
    }

    public double tan() {
        return size.y / size.x;
    }

    @Override
    public String toString() {
        return "{" + getName() + "," + getPosition() + "}";
    }

}