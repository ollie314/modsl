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

package org.modsl.collab;

import org.modsl.collab.layout.CollabDiagramMetricsAdjustment;
import org.modsl.collab.layout.CollabFRLayout;
import org.modsl.collab.layout.CollabInitialCirclePosition;
import org.modsl.collab.model.CollabDiagram;
import org.modsl.core.ModslProcessor;
import org.modsl.core.builder.AbstractBuilder;
import org.modsl.core.layout.AbstractLayout;
import org.modsl.core.layout.AbstractMetricsAdjustment;
import org.modsl.core.svg.AbstractSvgWriter;

public class CollabDiagramProcessor extends ModslProcessor<CollabDiagramLayoutProps, CollabDiagramTemplateProps, CollabDiagram> {

	public CollabDiagramProcessor() {
		super();
	}

	public CollabDiagramProcessor(String path) {
		super(path);
	}

	@Override
	protected AbstractBuilder getBuilder() {
		return new CollabDiagramBuilder();
	}

	@Override
	protected CollabDiagramLayoutProps getLayoutProps() {
		return new CollabDiagramLayoutProps(path, "collab");
	}

	@Override
	protected AbstractLayout[] getLayouts() {
		return new AbstractLayout[] { new CollabInitialCirclePosition(layoutProps), new CollabFRLayout(layoutProps) };
	}

	@Override
	protected AbstractMetricsAdjustment<CollabDiagram, CollabDiagramTemplateProps> getMetrics() {
		return new CollabDiagramMetricsAdjustment(templateProps);
	}

	@Override
	protected AbstractSvgWriter<CollabDiagram, CollabDiagramTemplateProps> getSvgWriter() {
		return new CollabDiagramSvgWriter(templateProps);
	}

	@Override
	protected CollabDiagramTemplateProps getTemplateProps() {
		return new CollabDiagramTemplateProps(path, "collab");
	}
}
