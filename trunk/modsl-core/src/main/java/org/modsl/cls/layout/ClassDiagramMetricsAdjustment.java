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

import org.apache.log4j.Logger;
import org.modsl.cls.ClassDiagramTemplateProps;
import org.modsl.cls.model.ClassConnector;
import org.modsl.cls.model.ClassDiagram;
import org.modsl.cls.model.ClassElement;
import org.modsl.core.layout.AbstractMetricsAdjustment;

public class ClassDiagramMetricsAdjustment extends AbstractMetricsAdjustment<ClassDiagram, ClassDiagramTemplateProps> {

	protected Logger log = Logger.getLogger(getClass());

	public ClassDiagramMetricsAdjustment(ClassDiagramTemplateProps props) {
		super(props);
	}

	public void apply(ClassDiagram diagram) {

		if (diagram.getRequestedSize().isZero()) {
			diagram.getRequestedSize().x = props.diagramDefaultWidth;
			diagram.getRequestedSize().y = props.diagramDefaultHeight;
		}

		diagram
				.setPaddingHeader((diagram.getName() == null ? 0 : props.diagramHeaderFT.getExtHeight(1))
						+ props.diagramPadding);
		diagram.setPaddingFooter(props.diagramFooterFT.getExtHeight(1) + props.diagramPadding);
		diagram.setPaddingSides(props.diagramPadding);
		for (ClassElement e : diagram.getElements()) {
			e.calcSize(props.elementHeaderFT, props.elementDetailFT);
		}
		for (ClassConnector c : diagram.getConnectors()) {
			c.calcSize(props.connectorFT);
		}

	}

}
