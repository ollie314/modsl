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

package org.modsl.cls;

import groovy.lang.Binding;
import groovy.util.ResourceException;
import groovy.util.ScriptException;

import java.io.FileNotFoundException;

import org.modsl.cls.layout.ClassDiagramMetricsAdjustment;
import org.modsl.cls.layout.ClassFRLayout;
import org.modsl.cls.layout.ClassInitialCirclePosition;
import org.modsl.cls.model.ClassDiagram;
import org.modsl.core.ModslEngine;

public class ClassEngine extends ModslEngine<ClassDiagramLayoutProps, ClassDiagramTemplateProps, ClassDiagram> {

	protected ClassDiagramBuilder builder;
	protected ClassDiagramMetricsAdjustment metrics;
	protected ClassInitialCirclePosition circleLayout;
	protected ClassFRLayout frLayout;
	protected ClassDiagramSvgWriter writer;

	public ClassEngine(String path) {

		layoutProps = new ClassDiagramLayoutProps(path, "cls");
		templateProps = new ClassDiagramTemplateProps(path, "cls");

		builder = new ClassDiagramBuilder();
		metrics = new ClassDiagramMetricsAdjustment(templateProps);
		circleLayout = new ClassInitialCirclePosition(layoutProps);
		frLayout = new ClassFRLayout(layoutProps);
		writer = new ClassDiagramSvgWriter(templateProps);

	}

	@Override
	protected void layout(ClassDiagram diagram) {
		circleLayout.apply(diagram);
		frLayout.apply(diagram);
	}

	@Override
	protected void metrics(ClassDiagram diagram) {
		metrics.apply(diagram);
	}

	@Override
	protected ClassDiagram parse(String fileName) throws ResourceException, ScriptException {
		Binding binding = new Binding();
		binding.setVariable("builder", new ClassDiagramBuilder());
		scriptEngine.run(fileName + ".modsl", binding);
		return (ClassDiagram) binding.getVariable("diagram");
	}

	@Override
	protected void render(ClassDiagram diagram) throws FileNotFoundException {
		writer.renderToFile(diagram, "etc/svg-out/" + "temp" + ".svg");
	}

}
