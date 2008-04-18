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
import org.modsl.cls.ClassDiagramConfig;
import org.modsl.cls.model.ClassDiagram;
import org.modsl.core.layout.AbstractLayout;

public class ClassDiagramRescale extends AbstractLayout<ClassDiagram, ClassDiagramConfig> {

	protected Logger log = Logger.getLogger(getClass());

	public ClassDiagramRescale(ClassDiagramConfig config) {
		super(config);
	}

	public void apply(ClassDiagram diagram) {
		if (diagram.getRequestedSize().isZero()) {
			diagram.getRequestedSize().x = config.diagramDefaultWidth;
			diagram.getRequestedSize().y = config.diagramDefaultHeight;
		}
		diagram.setPaddingHeader((diagram.getName() == null ? 0 : config.diagramHeaderFT.getExtHeight(1))
				+ config.diagramPadding);
		diagram.setPaddingFooter(config.diagramFooterFT.getExtHeight(1) + config.diagramPadding);
		diagram.setPaddingSides(config.diagramPadding);
		diagram.rescaleToRequestedSize();
	}
}
