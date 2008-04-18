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

/**
 * This test case is a good example of how to call ModSL from Java code to parse
 * an external Groovy diagram script.
 * 
 * @author avishnyakov
 */
public abstract class AbstractClassDiagramTest {
	/*
	 * 
	 * private static final String[] scriptRoots = new String[] {
	 * "./target/classes/samples/cls" }; private static GroovyScriptEngine
	 * scriptEngine;
	 * 
	 * static { try { scriptEngine = new GroovyScriptEngine(scriptRoots); }
	 * catch (IOException ex) {
	 * Logger.getLogger(AbstractClassDiagramTest.class).error(ex); } }
	 * 
	 * protected Logger log = Logger.getLogger(getClass());
	 * 
	 * public ClassDiagram processDiagram(String name) {
	 * 
	 * try {
	 * 
	 * Binding binding = new Binding(); binding.setVariable("builder", new
	 * ClassDiagramBuilder()); scriptEngine.run(name + ".modsl", binding);
	 * 
	 * ClassDiagram d = (ClassDiagram) binding.getVariable("diagram");
	 * assertNotNull(d);
	 * 
	 * ClassDiagramConfig cfg = new ClassDiagramConfig("/config", "cls");
	 * 
	 * new ClassCore(cfg.getLayoutProps()).apply(d);
	 * 
	 * ClassDiagramSvgWriter templ = new
	 * ClassDiagramSvgWriter(cfg.getTemplateProps()); String svg =
	 * templ.renderToFile(d, "etc/svg-out/" + name + ".svg");
	 * assertTrue(svg.indexOf("</svg>") > 0);
	 * 
	 * return d;
	 *  } catch (Exception ex) {
	 * 
	 * log.error(ex); return null;
	 *  }
	 *  }
	 */
}
