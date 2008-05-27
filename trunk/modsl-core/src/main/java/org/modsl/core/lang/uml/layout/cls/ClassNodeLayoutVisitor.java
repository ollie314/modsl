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

import java.util.Arrays;
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

		NodeLabel hl = getHeaderLabel(node);
        log.debug("Setting offset for " + hl);

		FontTransform fth = hl.getType().getConfig().getFontTransform();
		hl.setOffset(fth.getLeftPadding(), fth.getTopPadding());

		Pt nodeSize = new Pt(fth.getExtStringWidth(hl.getName()), fth.getExtHeight(1));

		// vars
		FontTransform ftv = UMLMetaType.CLASS_VAR_NODE_LABEL.getConfig().getFontTransform();
		List<NodeLabel> vls = getVarLabels(node);

		double var_y = nodeSize.y;
		for (int i = 0; i < vls.size(); i++) {
			NodeLabel l = vls.get(i);
			l.setOffset(ftv.getLeftPadding(), var_y + ftv.getExtPosition(i));
			nodeSize.x = max(nodeSize.x, ftv.getLeftPadding() + l.getSize().x + ftv.getRightPadding());
			nodeSize.y = l.getOffset().y + ftv.getHeight();
			log.debug("Setting offset for " + l);
		}

		// methods
		FontTransform ftm = UMLMetaType.CLASS_METHOD_NODE_LABEL.getConfig().getFontTransform();
		List<NodeLabel> mls = getMethodLabels(node);

		double method_y;
		if (vls.size() > 0) {
			nodeSize.y += ftv.getBottomPadding();
			method_y = nodeSize.y;
		} else {
			method_y = var_y;
		}

		for (int i = 0; i < mls.size(); i++) {
			NodeLabel l = mls.get(i);
			l.setOffset(ftm.getLeftPadding(), method_y + ftm.getExtPosition(i));
			nodeSize.x = max(nodeSize.x, ftm.getLeftPadding() + l.getSize().x + ftm.getRightPadding());
			nodeSize.y = l.getOffset().y + ftm.getHeight();
            log.debug("Setting offset for " + l);
		}

		// final node size adjustments
		if (mls.size() > 0) {
			nodeSize.y += ftm.getBottomPadding();
		}

		if (vls.size() == 0 && mls.size() == 0) {
			nodeSize.y += fth.getSize();
		}

		node.setSize(nodeSize.x, nodeSize.y);

	}

	List<NodeLabel> getMethodLabels(Node node) {
		return node.getLabels(Arrays.asList(new MetaType[] { UMLMetaType.CLASS_METHOD_NODE_LABEL,
				UMLMetaType.CLASS_STATIC_METHOD_NODE_LABEL }));
	}

	List<NodeLabel> getVarLabels(Node node) {
		return node.getLabels(Arrays.asList(new MetaType[] { UMLMetaType.CLASS_VAR_NODE_LABEL,
				UMLMetaType.CLASS_STATIC_VAR_NODE_LABEL }));
	}

	NodeLabel getHeaderLabel(Node node) {
		List<NodeLabel> ls = node.getLabels(UMLMetaType.CLASS_CLASS_NODE_LABEL);
		if (ls.isEmpty()) {
			ls = node.getLabels(UMLMetaType.CLASS_INTERFACE_NODE_LABEL);
		}
		return ls.get(0);
	}

}
