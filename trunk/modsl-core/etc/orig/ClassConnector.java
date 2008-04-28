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

package OBSOLETE.org.modsl.cls.model;

import static java.lang.Math.PI;

import java.util.Arrays;
import java.util.List;

import org.modsl.core.agt.model.FontTransform;

import OBSOLETE.org.modsl.core.model.XY;
import OBSOLETE.org.modsl.core.model.diagram.Connector;

/**
 * Class connector implementation
 * 
 * @author avishnyakov
 */
public class ClassConnector extends Connector<ClassDiagram, ClassElement> {

    protected static double arrowAngle;
    protected static double arrowLength;

    protected final static List<Type> hollowArrow = Arrays.asList(new Type[] { Type.EXTENDS, Type.IMPLEMENTS });

    protected String startMultiplicity, endMultiplicity;
    protected Type type;

    public ClassConnector(ClassDiagram parent) {
        super(parent, null);
    }

    public static enum Type {
        EXTENDS, IMPLEMENTS, AGGREGATION, COMPOSITION, ASSOCIATION
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
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
     * @return position of the multiplicity label at the start location
     */
    public XY getMultiStart() {
        double alpha = angle();
        double multiOffset = -arrowLength * 1.1d * Math.cos(arrowAngle / 2d);
        return new XY(multiOffset * Math.cos(alpha), multiOffset * Math.sin(alpha));
    }

    /**
     * @return position of the multiplicity label at the end location
     */
    public XY getMultiEnd() {
        double alpha = angle();
        double multiOffset = arrowLength * 1.75d * Math.cos(arrowAngle / 2d);
        return new XY(multiOffset * Math.cos(alpha), multiOffset * Math.sin(alpha));
    }

    /**
     * @return true if it's EXTENDS or IMPLEMENTS relationship
     */
    public boolean isHollowArrow() {
        return hollowArrow.contains(this.type);
    }

    /**
     * @return position of the middle point of the arrow
     */
    public XY getArrowMiddle() {
        double alpha = angle();
        double middleArrowLength = arrowLength * Math.cos(arrowAngle / 2d);
        return new XY(middleArrowLength * Math.cos(alpha), middleArrowLength * Math.sin(alpha));
    }

    /**
     * @return start multiplicity label
     */
    public String getStartMultiplicity() {
        return startMultiplicity;
    }

    /**
     * Sets start multiplicity label 
     * @param startMultiplicity
     */
    public void setStartMultiplicity(String startMultiplicity) {
        this.startMultiplicity = startMultiplicity;
    }

    /**
     * @return end multiplicity label
     */
    public String getEndMultiplicity() {
        return endMultiplicity;
    }

    /**
     * Sets end multiplicity label 
     * @param startMultiplicity
     */
    public void setEndMultiplicity(String endMultiplicity) {
        this.endMultiplicity = endMultiplicity;
    }

    public void calcSize(FontTransform connectorFT) {
        arrowAngle = PI / 4d;
        arrowLength = connectorFT.getArrowLength();
    }

}
