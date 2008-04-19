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

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.acos;
import static java.lang.Math.signum;

import org.modsl.core.model.XY;
import org.modsl.core.model.graph.Edge;
import org.modsl.core.model.graph.Vertex;

/**
 * Basic connector diagram element
 * @author avishnyakov
 *
 * @param <P> parent object (usually Diagram or it's subclass)
 * @param <E> class of the elements this connector class can connect
 */
public class Connector<P extends Diagram, E extends Element> extends AbstractDiagramObject<P> implements Edge {

    protected E startElement;
    protected E endElement;
    protected String endElementName;

    /**
     * New instance in the given parent diagram, with given name
     * @param parent
     * @param name
     */
    public Connector(P parent, String name) {
        super(parent, name);
        this.parent.addConnector(this);
    }

    public double angle() {
        XY delta = getDelta();
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
        XY delta = getDelta();
        return delta.x / getDelta().len();
    }

    /**
     * Convenience method to calculate adjusted position of the endpoint at the given element, 
     * considering that element's dimensions
     * @param e element
     * @param start when connector is directional, set to true if it starts at <code>e</code> 
     * @return adjusted (x, y) position
     */
    protected XY getAdjustedConnectorPosition(E e, boolean start) {
        XY ap = new XY();
        XY cp = e.getCenterPosition();
        XY s = e.getSize();
        double sign = start ? 1d : -1d;
        if (abs(e.tan()) > abs(tan())) {
            // i.e. line is crossing e2's side
            ap.x = cp.x + sign * s.x * signum(cos()) / 2d;
            ap.y = cp.y + sign * s.x * tan() * signum(cos()) / 2d;
        } else {
            ap.x = cp.x + sign * s.y / tan() * signum(sin()) / 2d;
            ap.y = cp.y + sign * s.y * signum(sin()) / 2d;
        }
        return ap;
    }

    public XY getAdjustedEndPosition() {
        return getAdjustedConnectorPosition(endElement, false);
    }

    public XY getAdjustedStartPosition() {
        return getAdjustedConnectorPosition(startElement, true);
    }

    /**
     * @return (delta(x), delta(y))
     */
    public XY getDelta() {
        return endElement.getCenterPosition().minus(startElement.getCenterPosition());
    }

    public E getEndElement() {
        return endElement;
    }

    public String getEndElementName() {
        return endElementName;
    }

    public Vertex getEndVertex() {
        return endElement;
    }

    public double getLength() {
        return getDelta().len();
    }

    public E getStartElement() {
        return startElement;
    }

    public Vertex getStartVertex() {
        return startElement;
    }

    public void setEndElement(E element) {
        endElement = element;
    }

    public void setEndElementName(String value) {
        this.endElementName = value;
    }

    public void setStartElement(E element) {
        startElement = element;
    }

    public double sin() {
        XY delta = getDelta();
        return delta.y / getDelta().len();
    }

    public double tan() {
        XY delta = getDelta();
        return delta.y / delta.x;
    }

    @Override
    public String toString() {
        return "{" + (startElement == null ? "null" : startElement.getName()) + ","
                + (endElement == null ? "null" : endElement.getName()) + "}";
    }

}