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
import org.modsl.core.agt.model.Pt;
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

        FontTransform ftn = node.getType().getConfig().getFontTransform();
        NodeLabel hl = getHeaderLabel(node);
        hl.setOffset(ftn.getLeftPadding(), ftn.getTopPadding());

        Pt nodeSize = new Pt(ftn.getExtStringWidth(hl.getName()), ftn.getExtHeight(1));

        // vars
        FontTransform ftv = UMLMetaType.CLASS_VAR_NODE_LABEL.getConfig().getFontTransform();
        List<NodeLabel> vls = getVarLabels(node);

        double vary = nodeSize.y;
        for (int i = 0; i < vls.size(); i++) {
            NodeLabel l = vls.get(i);
            l.setOffset(ftv.getLeftPadding(), vary + ftv.getExtPosition(i));
            nodeSize.x = max(nodeSize.x, ftv.getLeftPadding() + l.getSize().x + ftv.getRightPadding());
            nodeSize.y = l.getOffset().y + ftv.getHeight();
        }

        // methods
        FontTransform ftm = UMLMetaType.CLASS_METHOD_NODE_LABEL.getConfig().getFontTransform();
        List<NodeLabel> mls = getMethodLabels(node);

        double methody;
        if (vls.size() > 0) {
            nodeSize.y += ftv.getBottomPadding();
            methody = nodeSize.y;
        } else {
            methody = vary;
        }

        for (int i = 0; i < mls.size(); i++) {
            NodeLabel l = mls.get(i);
            l.setOffset(ftm.getLeftPadding(), methody + ftm.getExtPosition(i));
            nodeSize.x = max(nodeSize.x, ftm.getLeftPadding() + l.getSize().x + ftm.getRightPadding());
            nodeSize.y = l.getOffset().y + ftm.getHeight();
        }

        // final node size adjustments
        if (mls.size() > 0) {
            nodeSize.y += ftm.getBottomPadding();
        }

        if (mls.size() == 0 && mls.size() == 0) {
            nodeSize.y += ftn.getSize();
        }

        node.setSize(nodeSize.x, nodeSize.y);

    }

    List<NodeLabel> getMethodLabels(Node node) {
        List<NodeLabel> ls = node.getLabels(UMLMetaType.CLASS_METHOD_NODE_LABEL);
        ls.addAll(node.getLabels(UMLMetaType.CLASS_STATIC_METHOD_NODE_LABEL));
        return ls;
    }

    List<NodeLabel> getVarLabels(Node node) {
        List<NodeLabel> ls = node.getLabels(UMLMetaType.CLASS_VAR_NODE_LABEL);
        ls.addAll(node.getLabels(UMLMetaType.CLASS_STATIC_VAR_NODE_LABEL));
        return ls;
    }

    NodeLabel getHeaderLabel(Node node) {
        List<NodeLabel> ls = node.getLabels(UMLMetaType.CLASS_CLASS_NODE_LABEL);
        if (ls.isEmpty()) {
            ls = node.getLabels(UMLMetaType.CLASS_INTERFACE_NODE_LABEL);
        }
        return ls.get(0);
    }

}
