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

import org.modsl.cls.ClassDiagramConfig;
import org.modsl.cls.model.ClassConnector;
import org.modsl.cls.model.ClassDiagram;
import org.modsl.cls.model.ClassElement;
import org.modsl.core.layout.AbstractLayout;

/**
 * Update element sizes according to the font style and size
 * 
 * @author avishnyakov
 *
 */
public class ClassElementLayout extends AbstractLayout<ClassDiagram, ClassDiagramConfig> {

    public ClassElementLayout(ClassDiagramConfig config) {
        super(config);
    }

    public void apply(ClassDiagram diagram) {
        for (ClassElement e : diagram.getElements()) {
            e.calcSize(config.elementHeaderFT, config.elementDetailFT);
        }
        for (ClassConnector c : diagram.getConnectors()) {
            c.calcSize(config.connectorFT);
        }
    }
}
