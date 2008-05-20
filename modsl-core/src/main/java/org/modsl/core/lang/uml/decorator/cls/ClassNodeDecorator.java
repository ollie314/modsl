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

import org.modsl.core.agt.decor.AbstractDecorator;
import org.modsl.core.agt.model.Node;
import org.modsl.core.agt.model.Pt;

public class ClassNodeDecorator extends AbstractDecorator<Node> {

    @Override
    public void decorate(Node element) {
        super.decorate(element);
    }

    public Pt getHeaderLine1() {
        return new Pt(element.getPos().x, element.getType().getConfig().getFt().getExtHeight(1) + 2);
    }

    public Pt getHeaderLine2() {
        return new Pt(element.getPos().x + element.getSize().x, element.getType().getConfig().getFt().getExtHeight(1) + 2);
    }

}
