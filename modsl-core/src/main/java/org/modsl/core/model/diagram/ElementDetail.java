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

import org.modsl.core.model.XY;

public class ElementDetail<P extends Element> extends AbstractDiagramObject<P> {

    protected XY position = new XY();
    protected XY size = new XY();

    public ElementDetail(P parent, String name) {
        super(parent, name);
        this.parent.addElementDetail(this);
    }

    public XY getPosition() {
        return position;
    }

    public XY getSize() {
        return size;
    }

    public XY getAbsolutePosition() {
        return parent.position.plus(position);
    }

}