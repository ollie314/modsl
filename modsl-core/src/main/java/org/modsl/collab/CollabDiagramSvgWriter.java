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

import java.util.Collection;
import java.util.List;

import org.modsl.collab.model.CollabConnector;
import org.modsl.collab.model.CollabDiagram;
import org.modsl.collab.model.CollabElement;
import org.modsl.core.svg.AbstractSvgWriter;

/**
 * Renders collaboration diagram model as SVG through a collection of Groovy templates
 * 
 * @author avishnyakov
 */
public class CollabDiagramSvgWriter extends AbstractSvgWriter<CollabDiagram, CollabDiagramConfig> {

    public CollabDiagramSvgWriter(CollabDiagramConfig config) {
        super(config);
    }

    public String render(CollabDiagram diagram) {
        return renderDiagram(new StringBuffer(), diagram).toString();
    }

    private StringBuffer renderDiagram(StringBuffer sb, CollabDiagram d) {

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

    private void renderHistory(StringBuffer sb, List<CollabElement> elements) {
        if (props.renderHistory) {
            for (CollabElement e : elements) {
                invokeTemplate(sb, e, "element", "history");
            }
        }
    }

    private void renderConnectors(StringBuffer sb, Collection<CollabConnector> connectors) {
        for (CollabConnector c : connectors) {
            invokeTemplate(sb, c, "connector", "connector");
        }
    }

    private void renderElements(StringBuffer sb, Collection<CollabElement> elements) {
        for (CollabElement e : elements) {
            invokeTemplate(sb, e, "element", "element");
        }
    }

}
