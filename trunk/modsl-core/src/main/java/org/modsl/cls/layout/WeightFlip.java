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

package org.modsl.cls.layout;

import static java.lang.Math.min;

import org.apache.log4j.Logger;
import org.modsl.cls.ClassDiagramLayoutProps;
import org.modsl.cls.model.ClassDiagram;
import org.modsl.cls.model.ClassElement;
import org.modsl.core.layout.AbstractLayout;
import org.modsl.core.model.XY;

public class WeightFlip extends AbstractLayout<ClassDiagram, ClassDiagramLayoutProps> {

    protected Logger log = Logger.getLogger(getClass());

    public WeightFlip(ClassDiagramLayoutProps props) {
        super(props);
    }

    public void apply(ClassDiagram diagram) {
        double s = 0d, n = 0d, e = 0d, w = 0d;
        double halfx = diagram.getSize().x / 2d;
        double halfy = diagram.getSize().y / 2d;
        for (ClassElement ce : diagram.getElements()) {
            if (ce.getCenterPosition().x <= halfx) {
                n += ce.getWeight();
            } else {
                s += ce.getWeight();
            }
            if (ce.getCenterPosition().y <= halfy) {
                e += ce.getWeight();
            } else {
                w += ce.getWeight();
            }
        }
        double minw = min(n, min(s, min(e, w)));
        if (minw == s) {
            log.debug("South");
            for (ClassElement ce : diagram.getElements()) {
                ce.getPosition().y = diagram.getSize().y - ce.getPosition().y - ce.getSize().y;
            }
        } else if (minw == e) {
            log.debug("East");
            for (ClassElement ce : diagram.getElements()) {
                ce.setPosition(new XY(ce.getPosition().y, diagram.getSize().x - ce.getPosition().x - ce.getSize().x));
            }
        } else if (minw == w) {
            log.debug("West");
            for (ClassElement ce : diagram.getElements()) {
                ce.setPosition(new XY(ce.getPosition().y, ce.getPosition().x));
            }
        }
    }
}
