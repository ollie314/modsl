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

import groovy.lang.Binding;
import groovy.util.ResourceException;
import groovy.util.ScriptException;

import java.io.FileNotFoundException;

import org.modsl.collab.layout.CollabDiagramMetricsAdjustment;
import org.modsl.collab.layout.CollabFRLayout;
import org.modsl.collab.layout.CollabInitialCirclePosition;
import org.modsl.collab.model.CollabDiagram;
import org.modsl.core.ModslProcessor;

public class CollabDiagramProcessor extends ModslProcessor<CollabDiagramLayoutProps, CollabDiagramTemplateProps, CollabDiagram> {

	protected CollabDiagramBuilder builder;
	protected CollabDiagramMetricsAdjustment metrics;
	protected CollabInitialCirclePosition circleLayout;
	protected CollabFRLayout frLayout;
	protected CollabDiagramSvgWriter writer;

	public CollabDiagramProcessor(String path) {

		layoutProps = new CollabDiagramLayoutProps(path, "collab");
		templateProps = new CollabDiagramTemplateProps(path, "collab");

		builder = new CollabDiagramBuilder();
		metrics = new CollabDiagramMetricsAdjustment(templateProps);
		circleLayout = new CollabInitialCirclePosition(layoutProps);
		frLayout = new CollabFRLayout(layoutProps);
		writer = new CollabDiagramSvgWriter(templateProps);

	}

	@Override
	protected void layout(CollabDiagram diagram) {
		circleLayout.apply(diagram);
		frLayout.apply(diagram);
	}

	@Override
	protected void metrics(CollabDiagram diagram) {
		metrics.apply(diagram);
	}

	@Override
	protected CollabDiagram parse(String fileName) throws ResourceException, ScriptException {
		Binding binding = new Binding();
		binding.setVariable("builder", new CollabDiagramBuilder());
		scriptEngine.run(fileName + ".modsl", binding);
		return (CollabDiagram) binding.getVariable("diagram");
	}

	@Override
	protected void render(CollabDiagram diagram) throws FileNotFoundException {
		writer.renderToFile(diagram, "etc/svg-out/" + "temp" + ".svg");
	}

}
