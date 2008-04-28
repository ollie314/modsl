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

package OBSOLETE.org.modsl.collab.model;

import org.modsl.core.agt.model.FontTransform;

import OBSOLETE.org.modsl.core.model.diagram.Element;
import OBSOLETE.org.modsl.core.model.diagram.ElementDetail;

/**
 * Collaboraton element implementation
 * 
 * @author avishnyakov
 */
public class CollabElement extends Element<CollabDiagram, ElementDetail> {

    public CollabElement(String name, CollabDiagram parent) {
        super(parent, name);
    }

    /**
     * Calculate size given font transform
     * @param elementFT
     */
    public void calcSize(FontTransform elementFT) {
        size.x = elementFT.getExtStringWidth(name);
        size.y = elementFT.getExtHeight(1) + 2;
    }

}
