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

package org.modsl.collab.layout;

import org.apache.log4j.Logger;
import org.modsl.collab.CollabDiagramConfig;
import org.modsl.collab.model.CollabDiagram;
import org.modsl.core.layout.AbstractLayout;

public class CollabDiagramRescale extends AbstractLayout<CollabDiagram, CollabDiagramConfig> {

	protected Logger log = Logger.getLogger(getClass());

	public CollabDiagramRescale(CollabDiagramConfig config) {
		super(config);
	}

	public void apply(CollabDiagram diagram) {
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
