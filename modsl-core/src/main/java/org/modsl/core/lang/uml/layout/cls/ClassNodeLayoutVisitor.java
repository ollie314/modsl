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
import org.modsl.core.agt.layout.SimpleNodeLabelLayoutVisitor;
import org.modsl.core.agt.model.MetaType;
import org.modsl.core.agt.model.Node;
import org.modsl.core.agt.model.NodeLabel;
import org.modsl.core.lang.uml.UMLMetaType;

/**
 * Node size calculation based on node's text height and width.
 * @author avishnyakov
 */
public class ClassNodeLayoutVisitor extends SimpleNodeLabelLayoutVisitor {

    public ClassNodeLayoutVisitor(MetaType type) {
        super(type);
    }

    @Override
    public void apply(Node node) {

        // header
        FontTransform ftn = node.getType().getConfig().getFontTransform();
        NodeLabel hl = getHeaderLabel(node);
        hl.setOffset(ftn.getLeftPadding(), ftn.getTopPadding());
        
        double maxExtStringWidth = ftn.getExtStringWidth(hl.getName());
        double maxExtHeight = ftn.getExtHeight(1);

        // vars
        FontTransform ftv = UMLMetaType.CLASS_VAR_NODE_LABEL.getConfig().getFontTransform();
        List<NodeLabel> vls = node.getLabels(UMLMetaType.CLASS_VAR_NODE_LABEL);

        double vary = maxExtHeight;
        for (int i = 0; i < vls.size(); i++) {
            NodeLabel l = vls.get(i);
            l.setOffset(ftv.getLeftPadding(), vary + ftv.getExtPosition(i));
            maxExtStringWidth = max(maxExtStringWidth, ftv.getLeftPadding() + l.getSize().x + ftv.getRightPadding());
            maxExtHeight = l.getOffset().y + ftv.getHeight();
        }

        // methods
        FontTransform ftm = UMLMetaType.CLASS_METHOD_NODE_LABEL.getConfig().getFontTransform();
        List<NodeLabel> mls = node.getLabels(UMLMetaType.CLASS_METHOD_NODE_LABEL);
        
        double methody;
        if (vls.size() > 0) {
            maxExtHeight += ftv.getBottomPadding();
            methody = maxExtHeight;
        } else {
            methody = vary;
        }

        for (int i = 0; i < mls.size(); i++) {
            NodeLabel l = mls.get(i);
            l.setOffset(ftm.getLeftPadding(), methody + ftm.getExtPosition(i));
            maxExtStringWidth = max(maxExtStringWidth, ftm.getLeftPadding() + l.getSize().x + ftm.getRightPadding());
            maxExtHeight = l.getOffset().y + ftm.getHeight();
        }

        // final node size adjustments
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
