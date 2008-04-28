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

package OBSOLETE.org.modsl.collab;

import java.util.Collection;
import java.util.List;


import OBSOLETE.org.modsl.collab.model.CollabConnector;
import OBSOLETE.org.modsl.collab.model.CollabDiagram;
import OBSOLETE.org.modsl.collab.model.CollabElement;
import OBSOLETE.org.modsl.core.svg.AbstractSvgWriter;

/**
 * Renders collaboration diagram model as SVG through a collection of Groovy
 * templates
 * 
 * @author avishnyakov
 */
public class CollabDiagramSvgWriter extends AbstractSvgWriter<CollabDiagram, CollabDiagramTemplateProps> {

	public CollabDiagramSvgWriter(CollabDiagramTemplateProps props) {
		super(props);
	}

	public void render(CollabDiagram diagram) {
		diagram.setOutput(renderDiagram(new StringBuilder(), diagram).toString());
	}

	private StringBuilder renderDiagram(StringBuilder sb, CollabDiagram d) {

		invokeTemplate(sb, d, "diagram", "diagram_start");
		invokeTemplate(sb, d, "diagram", "diagram_stylesheet");

		renderHistory(sb, d.getElements());
		renderConnectors(sb, d.getConnectors());
		renderElements(sb, d.getElements());

		if (d.getName() != null) {
			invokeTemplate(sb, d, "diagram", "diagram_header");
		}

		// before the end tag completes
		d.timestamp("svg_rendering");

		invokeTemplate(sb, d, "diagram", "diagram_end");

		return sb;

	}

	private void renderHistory(StringBuilder sb, List<CollabElement> elements) {
		if (props.renderHistory) {
			for (CollabElement e : elements) {
				invokeTemplate(sb, e, "element", "history");
			}
		}
	}

	private void renderConnectors(StringBuilder sb, Collection<CollabConnector> connectors) {
		for (CollabConnector c : connectors) {
			invokeTemplate(sb, c, "connector", "connector");
		}
	}

	private void renderElements(StringBuilder sb, Collection<CollabElement> elements) {
		for (CollabElement e : elements) {
			invokeTemplate(sb, e, "element", "element");
		}
	}

}
