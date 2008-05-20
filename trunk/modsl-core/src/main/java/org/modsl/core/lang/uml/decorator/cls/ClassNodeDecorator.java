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

import java.util.Arrays;
import java.util.List;

import org.modsl.core.agt.decor.AbstractDecorator;
import org.modsl.core.agt.model.MetaType;
import org.modsl.core.agt.model.Node;
import org.modsl.core.agt.model.NodeLabel;
import org.modsl.core.agt.model.Pt;
import org.modsl.core.lang.uml.UMLMetaType;

public class ClassNodeDecorator extends AbstractDecorator<Node> {

    List<NodeLabel> vls;
    List<NodeLabel> mls;

    @Override
    public void decorate(Node element) {
        super.decorate(element);
        mls = element.getLabels(Arrays.asList(new MetaType[] { UMLMetaType.CLASS_METHOD_NODE_LABEL,
                UMLMetaType.CLASS_STATIC_METHOD_NODE_LABEL }));
        vls = element.getLabels(Arrays.asList(new MetaType[] { UMLMetaType.CLASS_VAR_NODE_LABEL,
                UMLMetaType.CLASS_STATIC_VAR_NODE_LABEL }));
    }

    public Pt getHeaderLine1() {
        return new Pt(element.getPos().x, element.getType().getConfig().getFt().getExtHeight(1) + 2);
    }

    public Pt getHeaderLine2() {
        return getHeaderLine1().incBy(element.getSize().x, 0);
    }

    public Pt getVLine1() {
        Pt hl = getHeaderLine1();
        if (vls.isEmpty() || mls.isEmpty()) {
            return hl;
        } else {
            return hl.incBy(0d, UMLMetaType.CLASS_VAR_NODE_LABEL.getConfig().getFt().getExtHeight(vls.size()) + 2);
        }
    }

    public Pt getVLine2() {
        if (vls.isEmpty() || mls.isEmpty()) {
            return getHeaderLine1();
        } else {
            return getVLine1().incBy(element.getSize().x, 0d);
        }
    }

}
