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

import static java.lang.Math.max;

import org.modsl.cls.ClassDiagramConfig;
import org.modsl.cls.model.ClassDiagram;
import org.modsl.cls.model.ClassElement;
import org.modsl.cls.model.ClassElementDetail;
import org.modsl.core.layout.AbstractLayout;

public class ElementLayout extends AbstractLayout<ClassDiagram, ClassDiagramConfig> {

	public ElementLayout(ClassDiagramConfig config) {
		super(config);
	}

	public void apply(ClassDiagram diagram) {
		for (ClassElement e : diagram.getElements()) {
			update(e);
		}
	}

	private void update(ClassElement element) {

		// position Y attributes
		int count = 0;
		double attrMaxY = config.elementHeaderFST.getHeight();
		for (ClassElementDetail ed : element.getAttributes()) {
			ed.getPosition().y = config.elementHeaderFST.getExtHeight(1) + config.elementDetailFST.getExtHeight(count);
			attrMaxY = ed.getPosition().y + config.elementDetailFST.getHeight() + config.elementDetailFST.getBottomTrailing();
			count++;
		}

		// position Y methods
		count = 0;
		for (ClassElementDetail ed : element.getMethods()) {
			ed.getPosition().y = attrMaxY + config.elementDetailFST.getExtHeight(count);
			count++;
		}

		// adjust X postion for all the details and check them for max X
		double maxExtStringWidth = 0;
		int maxLeading = max(config.elementDetailFST.getLeftLeading(), config.elementHeaderFST.getLeftLeading());
		for (ClassElementDetail ed : element.getDetails()) {
			ed.getPosition().x = maxLeading;
			maxExtStringWidth = max(maxExtStringWidth, config.elementDetailFST.getStringWidth(ed.getName()));
		}
		maxExtStringWidth += config.elementDetailFST.getRightTrailing();

		// then check and position the header
		maxExtStringWidth = max(maxExtStringWidth, config.elementHeaderFST.getStringWidth(element.getName()));

		element.getSize().x = maxExtStringWidth;
		element.getSize().y = config.elementHeaderFST.getExtHeight(1)
				+ config.elementDetailFST.getExtHeight(element.getAttributes().size())
				+ config.elementDetailFST.getExtHeight(element.getMethods().size());

	}
	
}
