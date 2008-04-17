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

import java.util.Collection;
import java.util.List;

import org.modsl.cls.model.ClassConnector;
import org.modsl.cls.model.ClassDiagram;
import org.modsl.cls.model.ClassElement;
import org.modsl.cls.model.ClassElementDetail;
import org.modsl.core.svg.AbstractSvgWriter;

/**
 * Renders class diagram model as SVG 
 * 
 * @author avishnyakov
 *
 */
public class ClassDiagramSvgWriter extends AbstractSvgWriter<ClassDiagramConfig> {

    public ClassDiagramSvgWriter(ClassDiagramConfig config) {
        super(config);
    }

    public String render(ClassDiagram diagram) {
        return renderDiagram(new StringBuffer(), diagram).toString();
    }

    private StringBuffer renderDiagram(StringBuffer sb, ClassDiagram d) {
        invokeTemplate(sb, d, "diagram", "diagram_start");
        invokeTemplate(sb, d, "diagram", "diagram_stylesheet");
        renderHistory(sb, d.getElements());
        renderConnectors(sb, d.getConnectors());
        renderElements(sb, d.getElements());
        if (d.getName() != null) {
            invokeTemplate(sb, d, "diagram", "diagram_header");
        }
        d.timestamp("svg_rendering");
        invokeTemplate(sb, d, "diagram", "diagram_end");
        return sb;
    }

    private void renderHistory(StringBuffer sb, List<ClassElement> elements) {
        if (config.renderHistory) {
            for (ClassElement e : elements) {
                invokeTemplate(sb, e, "element", "history");
            }
        }
    }

    private void renderConnectors(StringBuffer sb, Collection<ClassConnector> connectors) {
        for (ClassConnector c : connectors) {
            invokeTemplate(sb, c, "connector", "connector");
        }
    }

    private void renderElements(StringBuffer sb, Collection<ClassElement> elements) {
        for (ClassElement e : elements) {
            invokeTemplate(sb, e, "element", "element");
            renderElementDetails(sb, e.getDetails());
        }
    }

    private void renderElementDetails(StringBuffer sb, List<ClassElementDetail> details) {
        for (ClassElementDetail ed : details) {
            invokeTemplate(sb, ed, "element_detail", "element_detail");
        }
    }

}
