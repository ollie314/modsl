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

import org.modsl.cls.layout.ClassDiagramMetricsAdjustment;
import org.modsl.cls.layout.ClassFRLayout;
import org.modsl.cls.layout.ClassInitialCirclePosition;
import org.modsl.cls.model.ClassDiagram;
import org.modsl.core.ModslProcessor;
import org.modsl.core.builder.AbstractBuilder;
import org.modsl.core.layout.AbstractLayout;
import org.modsl.core.layout.AbstractMetricsAdjustment;
import org.modsl.core.svg.AbstractSvgWriter;

public class ClassDiagramProcessor extends ModslProcessor<ClassDiagramLayoutProps, ClassDiagramTemplateProps, ClassDiagram> {

	public ClassDiagramProcessor() {
		super();
	}

	public ClassDiagramProcessor(String path) {
		super(path);
	}

	@Override
	protected AbstractBuilder getBuilder() {
		return new ClassDiagramBuilder();
	}

	@Override
	protected ClassDiagramLayoutProps getLayoutProps() {
		return new ClassDiagramLayoutProps(path, "cls");
	}

	@Override
	protected AbstractLayout[] getLayouts() {
		return new AbstractLayout[] { new ClassInitialCirclePosition(layoutProps), new ClassFRLayout(layoutProps) };
	}

	@Override
	protected AbstractMetricsAdjustment<ClassDiagram, ClassDiagramTemplateProps> getMetrics() {
		return new ClassDiagramMetricsAdjustment(templateProps);
	}

	@Override
	protected AbstractSvgWriter<ClassDiagram, ClassDiagramTemplateProps> getSvgWriter() {
		return new ClassDiagramSvgWriter(templateProps);
	}

	@Override
	protected ClassDiagramTemplateProps getTemplateProps() {
		return new ClassDiagramTemplateProps(path, "cls");
	}

}
