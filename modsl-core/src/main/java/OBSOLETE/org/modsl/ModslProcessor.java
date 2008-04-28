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

package OBSOLETE.org.modsl;

import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;

import java.io.FileOutputStream;
import java.io.PrintStream;

import org.apache.log4j.Logger;

import OBSOLETE.org.modsl.core.builder.AbstractBuilder;
import OBSOLETE.org.modsl.core.config.AbstractLayoutProps;
import OBSOLETE.org.modsl.core.config.AbstractTemplateProps;
import OBSOLETE.org.modsl.core.layout.AbstractLayout;
import OBSOLETE.org.modsl.core.layout.AbstractMetricsAdjustment;
import OBSOLETE.org.modsl.core.model.diagram.Diagram;
import OBSOLETE.org.modsl.core.svg.AbstractSvgWriter;

/**
 * Core process, aggregates parsing, layout and rendering.
 * 
 * @author avishnyakov
 * 
 */
public abstract class ModslProcessor<LP extends AbstractLayoutProps, TP extends AbstractTemplateProps, D extends Diagram<?, ?, ?>> {

	private static final Logger log = Logger.getLogger(ModslProcessor.class);

	private static GroovyScriptEngine scriptEngine;

	static {
		try {
			scriptEngine = new GroovyScriptEngine(new String[] { "." });
		} catch (Exception ex) {
			log.error("Failed to load scripting engine", ex);
		}
	}

	protected String path = "/config";

	protected LP layoutProps;

	protected TP templateProps;

	public ModslProcessor() {
		this.layoutProps = getLayoutProps();
		this.templateProps = getTemplateProps();
	}

	public ModslProcessor(String path) {
		this.path = path;
		this.layoutProps = getLayoutProps();
		this.templateProps = getTemplateProps();
	}

	public D process(String fileIn, String fileOut) {

		try {

			GroovyScriptEngine scriptEngine = new GroovyScriptEngine(new String[] { "." });
			Binding binding = new Binding();
			binding.setVariable("builder", getBuilder());
			scriptEngine.run(fileIn, binding);
			D diagram = (D) binding.getVariable("diagram");

			diagram.timestamp("parsing");

			getMetrics().apply(diagram);

			for (AbstractLayout<D, LP> layout : getLayouts()) {
				layout.apply(diagram);
			}

			diagram.rescaleToRequestedSize();

			diagram.timestamp("layout");

			getSvgWriter().render(diagram);

			PrintStream p = new PrintStream(new FileOutputStream(fileOut));
			p.print(diagram.getOutput());
			p.close();

			return diagram;

		} catch (Exception ex) {
			log.error("Error while processing diagram script " + fileIn, ex);
		}

		return null;

	}

	protected abstract AbstractBuilder getBuilder();

	protected abstract LP getLayoutProps();

	protected abstract AbstractLayout<D, LP>[] getLayouts();

	protected abstract AbstractMetricsAdjustment<D, TP> getMetrics();

	protected abstract AbstractSvgWriter<D, TP> getSvgWriter();

	protected abstract TP getTemplateProps();

}
