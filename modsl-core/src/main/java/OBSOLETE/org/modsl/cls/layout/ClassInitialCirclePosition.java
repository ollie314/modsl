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

package OBSOLETE.org.modsl.cls.layout;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.floor;
import static java.lang.Math.random;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

import org.apache.log4j.Logger;

import OBSOLETE.org.modsl.cls.ClassDiagramLayoutProps;
import OBSOLETE.org.modsl.cls.model.ClassDiagram;
import OBSOLETE.org.modsl.cls.model.ClassElement;
import OBSOLETE.org.modsl.core.layout.AbstractLayout;
import OBSOLETE.org.modsl.core.model.XY;

/**
 * Lays the element out on a circle to reduce edge crossing in the FR algorithm later
 * @author avishnyakov
 *
 */
public class ClassInitialCirclePosition extends AbstractLayout<ClassDiagram, ClassDiagramLayoutProps> {

    private Logger log = Logger.getLogger(getClass());

    protected double angle;
    protected ClassDiagram diag;
    protected int circlePositions;

    public ClassInitialCirclePosition(ClassDiagramLayoutProps props) {
        super(props);
    }

    public void apply(ClassDiagram diag) {

        this.diag = diag;
        this.circlePositions = diag.getElements().size();
        this.angle = 2d * PI / circlePositions;

        diag.calcElementWeights();

        initCircle();
        optimizeEdgeLength();

    }

    private void optimizeEdgeLength() {
        circlePositions = diag.getElements().size();
        for (int i = 0; i < props.frInitMaxRounds * circlePositions; i++) {
            double curLen = diag.getSumEdgeLength();
            int p1 = (int) floor(random() * circlePositions);
            int p2 = (int) floor(random() * circlePositions);
            swap(p1, p2);
            if (diag.getSumEdgeLength() > curLen) {
                swap(p1, p2);
            }
        }
    }

    private void initCircle() {
        for (int iv = 0; iv < diag.getElements().size(); iv++) {
            setCirclePosition(diag.getElement(iv), iv);
        }
    }

    private void setCirclePosition(ClassElement e, int p) {
        e.getPosition().x = getRequestedSizeDiagonal() / 2d * cos(getPositionAngle(p));
        e.getPosition().y = getRequestedSizeDiagonal() / 2d * sin(getPositionAngle(p));
    }

    private double getRequestedSizeDiagonal() {
        return sqrt(diag.getRequestedSize().x * diag.getRequestedSize().x + diag.getRequestedSize().y * diag.getRequestedSize().y);
    }

    private double getPositionAngle(int p) {
        return PI / 4d - angle * p;
    }

    private void swap(int p1, int p2) {
        ClassElement e1 = diag.getElement(p1);
        ClassElement e2 = diag.getElement(p2);
        XY xy1 = e1.getPosition();
        e1.setPosition(e2.getPosition());
        e2.setPosition(xy1);
    }

}
