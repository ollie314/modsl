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

	private double getTextWidth(ClassElement e) {
		return config.elementHeaderFST.stringWidth(e.getName());
	}

	private double getTextWidth(ClassElementDetail ed) {
		return config.elementDetailFST.stringWidth(ed.getName());
	}

	private void update(ClassElement element) {

		// position Y attributes
		int count = 0;
		double attrMaxY = config.elementHeaderFST.getYSize();
		for (ClassElementDetail ed : element.getAttributes()) {
			ed.getPosition().y = config.elementHeaderFST.getYSize() + config.elementDetailFST.getYLead()
					+ (config.elementDetailFST.getFontSize() + config.elementDetailFST.getYGap()) * count;
			attrMaxY = ed.getPosition().y + config.elementDetailFST.getFontSize() + config.elementDetailFST.getYTrail();
			count++;
		}

		// position Y methods
		count = 0;
		for (ClassElementDetail ed : element.getMethods()) {
			ed.getPosition().y = attrMaxY + config.elementDetailFST.getYLead()
					+ (config.elementDetailFST.getFontSize() + config.elementDetailFST.getYGap()) * count;
			count++;
		}

		// adjust X postion for all the details and check them for max X
		double maxX = 0;
		for (ClassElementDetail ed : element.getDetails()) {
			int x = max(config.elementDetailFST.getXLead(), config.elementHeaderFST.getXLead());
			ed.getPosition().x = x;
			maxX = max(maxX, x + getTextWidth(ed));
		}
		maxX += config.elementDetailFST.getXTrail();

		// then check and position the header
		maxX = max(maxX, getTextWidth(element));

		element.getSize().x = config.elementHeaderFST.getXLead() + maxX + config.elementHeaderFST.getXTrail();
		element.getSize().y = config.elementHeaderFST.getYSize()
				+ max(config.elementDetailFST.getYStack(element.getAttributes().size())
						+ config.elementDetailFST.getYStack(element.getMethods().size()), config.elementDetailFST.getYLead()
						+ config.elementDetailFST.getYTrail());

	}

}
