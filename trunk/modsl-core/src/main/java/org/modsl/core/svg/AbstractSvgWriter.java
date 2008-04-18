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

package org.modsl.core.svg;

import groovy.text.GStringTemplateEngine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.modsl.core.config.AbstractProps;
import org.modsl.core.model.diagram.AbstractDiagramObject;
import org.modsl.core.model.diagram.Diagram;

public abstract class AbstractSvgWriter<D extends Diagram, P extends AbstractProps> {

    protected static final String TEMPLATE_FILE_EXT = ".tpl";
    protected static GStringTemplateEngine engine = new GStringTemplateEngine();

    private final Logger log = Logger.getLogger(getClass());

    protected Map<String, groovy.text.Template> templateCache = new TreeMap<String, groovy.text.Template>();
    protected P props;

    public AbstractSvgWriter(P props) {
        this.props = props;
    }

    public abstract String render(D diagram);

    public String renderToFile(D diagram, String fileName) throws FileNotFoundException {
        PrintStream p = new PrintStream(new FileOutputStream(fileName));
        String str = render(diagram);
        p.print(str);
        p.close();
        return str;
    }

    protected void invokeTemplate(StringBuffer sb, AbstractDiagramObject<?> diagramObject, String diagramObjectName,
            String templateName) {
        if (diagramObject.isVisible()) {
            Map<String, Object> bindings = bind(diagramObjectName, diagramObject);
            String fullTemplateName = getTemplateFileName(templateName);
            try {
                groovy.text.Template template = getTemplate(fullTemplateName);
                sb.append(template.make(bindings));
            } catch (Exception ex) {
                log.debug("Invoking template " + fullTemplateName + " with binding " + diagramObjectName + "("
                        + diagramObject.getClass() + ")", ex);
            }
        }
    }

    private synchronized groovy.text.Template getTemplate(String fullTemplateName) throws Exception {
        groovy.text.Template template = templateCache.get(fullTemplateName);
        if (template == null) {
            InputStream is = getClass().getResourceAsStream(fullTemplateName);
            if (is == null) {
                throw new RuntimeException("Cannot find template " + fullTemplateName);
            }
            BufferedReader r = new BufferedReader(new InputStreamReader(is));
            template = engine.createTemplate(r);
            templateCache.put(fullTemplateName, template);
            r.close();
        }
        return template;
    }

    private String getTemplateFileName(String element) {
        return props.getPath() + element + TEMPLATE_FILE_EXT;
    }

    private Map<String, Object> bind(String key, Object value) {
        Map<String, Object> b = new HashMap<String, Object>();
        b.put("config", props);
        b.put(key, value);
        return b;
    }
}
