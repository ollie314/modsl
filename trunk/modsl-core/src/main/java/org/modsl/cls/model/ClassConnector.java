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

package org.modsl.cls.model;

import static java.lang.Math.PI;

import java.util.Arrays;
import java.util.List;

import org.modsl.core.model.XY;
import org.modsl.core.model.diagram.Connector;

/**
 * Class connector implementation
 * 
 * @author avishnyakov
 */
public class ClassConnector extends Connector<ClassDiagram, ClassElement> {

    protected static double arrowAngle = PI / 4d;
    protected static double arrowLength = 20;

    protected final static List<Type> hollowArrow = Arrays.asList(new Type[] { Type.EXTENSION, Type.IMPLEMENTATION });

    protected String startMultiplicity, endMultiplicity;
    protected Type type;

    public ClassConnector(String metaKey, ClassDiagram parent) {
        super(metaKey, parent, null);
    }

    public static enum Type {
        EXTENSION, IMPLEMENTATION, AGGREGATION, COMPOSITION, ASSOCIATION
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public XY getArrow1() {
        double alpha = angle() - arrowAngle / 2d;
        return new XY(arrowLength * Math.cos(alpha), arrowLength * Math.sin(alpha));
    }

    public XY getArrow2() {
        double alpha = angle() + arrowAngle / 2d;
        return new XY(arrowLength * Math.cos(alpha), arrowLength * Math.sin(alpha));
    }

    public XY getMultiStart() {
        double alpha = angle();
        double multiOffset = -arrowLength * 1.1d * Math.cos(arrowAngle / 2d);
        return new XY(multiOffset * Math.cos(alpha), multiOffset * Math.sin(alpha));
    }

    public XY getMultiEnd() {
        double alpha = angle();
        double multiOffset = arrowLength * 1.75d * Math.cos(arrowAngle / 2d);
        return new XY(multiOffset * Math.cos(alpha), multiOffset * Math.sin(alpha));
    }

    public boolean isHollowArrow() {
        return hollowArrow.contains(this.type);
    }

    public XY getArrowMiddle() {
        double alpha = angle();
        double middleArrowLength = arrowLength * Math.cos(arrowAngle / 2d);
        return new XY(middleArrowLength * Math.cos(alpha), middleArrowLength * Math.sin(alpha));
    }

    public String getStartMultiplicity() {
        return startMultiplicity;
    }

    public void setStartMultiplicity(String startMultiplicity) {
        this.startMultiplicity = startMultiplicity;
    }

    public String getEndMultiplicity() {
        return endMultiplicity;
    }

    public void setEndMultiplicity(String endMultiplicity) {
        this.endMultiplicity = endMultiplicity;
    }
}
