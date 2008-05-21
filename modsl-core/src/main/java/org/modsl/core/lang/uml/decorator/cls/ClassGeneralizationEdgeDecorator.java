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

package org.modsl.core.lang.uml.decorator.cls;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import org.modsl.core.agt.decor.AbstractDecorator;
import org.modsl.core.agt.model.Edge;
import org.modsl.core.agt.model.Pt;

abstract public class ClassGeneralizationEdgeDecorator extends AbstractDecorator<Edge> {

    double arrowAngle = PI / 3.5d;
    double arrowLength;

    /**
     * @return position of the left arrow's side
     */
    public Pt getArrowLeft() {
        return getOffsetPoint(element.angle2() - arrowAngle / 2d, arrowLength);
    }

    /**
     * @return position of the right arrow's side
     */
    public Pt getArrowRight() {
        return getOffsetPoint(element.angle2() + arrowAngle / 2d, arrowLength);
    }

    /**
     * @return position of the middle point of the arrow
     */
    public Pt getArrowMiddle() {
        return getOffsetPoint(element.angle2(), arrowLength * cos(arrowAngle / 2d));
    }

    Pt getOffsetPoint(double alpha, double len) {
        return new Pt(element.getNode2Port().x - len * cos(alpha), element.getNode2Port().y - len * sin(alpha));
    }

}
