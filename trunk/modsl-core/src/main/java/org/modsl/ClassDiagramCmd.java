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

package org.modsl;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.io.File;
import java.io.IOException;

import org.codehaus.groovy.control.CompilationFailedException;
import org.modsl.cls.ClassDiagramBuilder;
import org.modsl.cls.ClassDiagramLayoutProps;
import org.modsl.cls.ClassDiagramSvgWriter;
import org.modsl.cls.ClassDiagramTemplateProps;
import org.modsl.cls.layout.ClassDiagramLayout;
import org.modsl.cls.model.ClassDiagram;
import org.modsl.core.config.Config;

/**
 * Command line utility to process a diagram script
 * 
 * @author avishnyakov
 *
 */
public class ClassDiagramCmd {

    public static void main(String[] args) throws CompilationFailedException, IOException {
        new ClassDiagramCmd().run(args);
    }

    private void run(String[] args) throws CompilationFailedException, IOException {

        if (args.length < 2) {
            System.err.println("Usage: " + getClass().getName() + " input_file_name output_file_name");
        }

        Binding binding = new Binding();
        binding.setVariable("builder", new ClassDiagramBuilder());
        GroovyShell shell = new GroovyShell(binding);
        shell.evaluate(new File(args[0]));

        ClassDiagram d = (ClassDiagram) binding.getVariable("diagram");
        Config<ClassDiagramTemplateProps, ClassDiagramLayoutProps> cfg = new Config<ClassDiagramTemplateProps, ClassDiagramLayoutProps>(
                "/config", "cls", new ClassDiagramTemplateProps(), new ClassDiagramLayoutProps());
        new ClassDiagramLayout(cfg.getLayoutProps()).apply(d);
        ClassDiagramSvgWriter templ = new ClassDiagramSvgWriter(cfg.getTemplateProps());
        templ.renderToFile(d, args[1]);

    }
}
