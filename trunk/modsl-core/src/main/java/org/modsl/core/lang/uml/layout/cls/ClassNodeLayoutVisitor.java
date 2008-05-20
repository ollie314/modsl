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

package org.modsl.core.lang.uml.layout.cls;

import static java.lang.Math.max;

import java.util.List;

import org.modsl.core.agt.common.FontTransform;
import org.modsl.core.agt.layout.SimpleNodeLabelSizeLayoutVisitor;
import org.modsl.core.agt.model.MetaType;
import org.modsl.core.agt.model.Node;
import org.modsl.core.agt.model.NodeLabel;
import org.modsl.core.lang.uml.UMLMetaType;

/**
 * Node size calculation based on node's text height and width.
 * @author avishnyakov
 */
public class ClassNodeLayoutVisitor extends SimpleNodeLabelSizeLayoutVisitor {

    public ClassNodeLayoutVisitor(MetaType type) {
        super(type);
    }

    @Override
    public void apply(Node node) {

        FontTransform ftn = node.getType().getConfig().getFontTransform();
        FontTransform ftv = UMLMetaType.CLASS_VAR_NODE_LABEL.getConfig().getFontTransform();
        FontTransform ftm = UMLMetaType.CLASS_METHOD_NODE_LABEL.getConfig().getFontTransform();

        NodeLabel hl = getHeaderLabel(node);
        List<NodeLabel> vls = node.getLabels(UMLMetaType.CLASS_VAR_NODE_LABEL);
        List<NodeLabel> mls = node.getLabels(UMLMetaType.CLASS_METHOD_NODE_LABEL);

        double maxExtStringWidth = ftn.getExtStringWidth(hl.getName());
        double maxExtHeight = ftn.getExtHeight(1);

        double varAreaY = maxExtHeight;
        for (int i = 0; i < vls.size(); i++) {
            NodeLabel l = vls.get(i);
            l.getOffset().x = ftv.getLeftPadding();
            l.getOffset().y = varAreaY + ftv.getExtPosition(i);
            maxExtStringWidth = max(maxExtStringWidth, ftv.getLeftPadding() + l.getSize().x + ftv.getRightPadding());
            maxExtHeight = l.getOffset().y + ftv.getHeight();
        }

        double methodAreaY;
        if (vls.size() > 0) {
            maxExtHeight += ftv.getBottomPadding();
            methodAreaY = maxExtHeight;
        } else {
            methodAreaY = varAreaY;
        }

        for (int i = 0; i < mls.size(); i++) {
            NodeLabel l = mls.get(i);
            l.getOffset().x = ftm.getLeftPadding();
            l.getOffset().y = methodAreaY + ftm.getExtPosition(i);
            maxExtStringWidth = max(maxExtStringWidth, ftm.getLeftPadding() + l.getSize().x + ftm.getRightPadding());
            maxExtHeight = l.getOffset().y + ftm.getHeight();
        }

        if (mls.size() > 0) {
            maxExtHeight += ftm.getBottomPadding();
        }

        if (mls.size() == 0 && mls.size() == 0) {
            maxExtHeight += ftn.getSize();
        }

        node.setSize(maxExtStringWidth, maxExtHeight);

    }

    NodeLabel getHeaderLabel(Node node) {
        List<NodeLabel> ls = node.getLabels(UMLMetaType.CLASS_CLASS_NODE_LABEL);
        if (ls.isEmpty()) {
            ls = node.getLabels(UMLMetaType.CLASS_INTERFACE_NODE_LABEL);
        }
        return ls.get(0);
    }

}
