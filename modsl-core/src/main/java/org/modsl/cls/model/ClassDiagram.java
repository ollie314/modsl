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

import java.util.Collections;
import java.util.Comparator;

import org.modsl.core.model.diagram.Diagram;

/**
 * Class diagram implementation
 * 
 * @author avishnyakov
 */
public class ClassDiagram extends Diagram<Object, ClassElement, ClassConnector> {

    public ClassDiagram(String name) {
        super(null, name);
    }

    public void sortElementsByWeight() {
        Collections.sort(orderedElements, new Comparator<ClassElement>() {
            public int compare(ClassElement e1, ClassElement e2) {
                return (int) ((e1.getWeight() - e2.getWeight()) * 1000);
            }
        });
    }

    public void calcElementWeights() {
        for (ClassElement e : orderedElements) {
            e.calcWeight();
        }
    }

}
