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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.modsl.cls.ClassDiagramLayoutProps;
import org.modsl.cls.ClassDiagramSvgWriter;
import org.modsl.cls.ClassDiagramTemplateProps;
import org.modsl.cls.layout.ClassDiagramLayout;
import org.modsl.collab.layout.CollabDiagramLayout;
import org.modsl.collab.model.CollabDiagram;
import org.modsl.core.config.Config;

/**
 * This test case is a good example of how to call ModSL from Java code to parse
 * an external Groovy diagram script.
 * 
 * @author avishnyakov
 */
public abstract class AbstractCollabDiagramTest {

    private static final String[] scriptRoots = new String[] { "./target/classes/samples/collab" };
    private static GroovyScriptEngine scriptEngine;

    static {
        try {
            scriptEngine = new GroovyScriptEngine(scriptRoots);
        } catch (IOException ex) {
            Logger.getLogger(AbstractCollabDiagramTest.class).error(ex);
        }
    }

    protected Logger log = Logger.getLogger(getClass());

    public CollabDiagram processDiagram(String name) {

        try {

            Binding binding = new Binding();
            binding.setVariable("builder", new CollabDiagramBuilder());
            scriptEngine.run(name + ".modsl", binding);

            CollabDiagram d = (CollabDiagram) binding.getVariable("diagram");
            assertNotNull(d);

            Config<CollabDiagramTemplateProps, CollabDiagramLayoutProps> cfg = new Config<CollabDiagramTemplateProps, CollabDiagramLayoutProps>(
                    "/config", "cls", new CollabDiagramTemplateProps(), new CollabDiagramLayoutProps());
            
            new CollabDiagramLayout(cfg.getLayoutProps()).apply(d);
            
            CollabDiagramSvgWriter templ = new CollabDiagramSvgWriter(cfg.getTemplateProps());

            String svg = templ.renderToFile(d, "etc/svg-out/" + name + ".svg");
            assertTrue(svg.indexOf("</svg>") > 0);

            return d;

        } catch (Exception ex) {

            log.error(getClass() + " failed:", ex);
            return null;

        }

    }

}
