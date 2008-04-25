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

package org.modsl.collab.model;

import static java.lang.Math.PI;

import org.modsl.core.agt.model.FontTransform;
import org.modsl.core.model.XY;
import org.modsl.core.model.diagram.Connector;

/**
 * Collaboration connector implementation
 * 
 * @author avishnyakov
 */
public class CollabConnector extends Connector<CollabDiagram, CollabElement> {

    protected static double arrowAngle;
    protected static double arrowLength;

    public CollabConnector(CollabDiagram parent, String name) {
        super(parent, name);
    }

    /**
     * @return position of the left arrow's side
     */
    public XY getArrow1() {
        double alpha = angle() - arrowAngle / 2d;
        return new XY(arrowLength * Math.cos(alpha), arrowLength * Math.sin(alpha));
    }

    /**
     * @return position of the right arrow's side
     */
    public XY getArrow2() {
        double alpha = angle() + arrowAngle / 2d;
        return new XY(arrowLength * Math.cos(alpha), arrowLength * Math.sin(alpha));
    }

    /**
     * @return position of the connector's midpoint
     */
    public XY getMidPoint() {
        return startElement.getCenterPosition()
                .plus(endElement.getCenterPosition().minus(startElement.getCenterPosition()).div(2d));
    }

    public void calcSize(FontTransform connectorFT) {
        arrowAngle = PI / 5d;
        arrowLength = connectorFT.getArrowLength();
    }

}
