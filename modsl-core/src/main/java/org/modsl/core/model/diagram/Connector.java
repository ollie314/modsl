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

public class Connector<P extends Diagram, E extends Element> extends AbstractDiagramObject<P> implements Edge {

    protected E startElement;
    protected E endElement;
    protected String endElementName;

    public Connector(P parent, String name) {
        super(parent, name);
        this.parent.addConnector(this);
    }

    public E getEndElement() {
        return endElement;
    }

    public E getStartElement() {
        return startElement;
    }

    public void setEndElement(E element) {
        endElement = element;
    }

    public void setStartElement(E element) {
        startElement = element;
    }

    public Vertex getStartVertex() {
        return startElement;
    }

    public Vertex getEndVertex() {
        return endElement;
    }

    public double getLength() {
        return getDelta().len();
    }

    public String toString() {
        return "{" + (startElement == null ? "null" : startElement.getName()) + ","
                + (endElement == null ? "null" : endElement.getName()) + "}";
    }

    public XY getDelta() {
        return endElement.getCenterPosition().minus(startElement.getCenterPosition());
    }

    public double tan() {
        XY delta = getDelta();
        return delta.y / delta.x;
    }

    public double sin() {
        XY delta = getDelta();
        return delta.y / getDelta().len();
    }

    public double cos() {
        XY delta = getDelta();
        return delta.x / getDelta().len();
    }

    public XY getAdjustedStartPosition() {
        return getAdjustedConnectorPosition(startElement, true);
    }

    public XY getAdjustedEndPosition() {
        return getAdjustedConnectorPosition(endElement, false);
    }

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

    public String getEndElementName() {
        return endElementName;
    }

    public void setEndElementName(String value) {
        this.endElementName = value;
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

}