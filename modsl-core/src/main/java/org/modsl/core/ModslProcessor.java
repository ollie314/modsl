package org.modsl.core;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.io.File;

import org.apache.log4j.Logger;
import org.modsl.core.builder.AbstractBuilder;
import org.modsl.core.config.AbstractLayoutProps;
import org.modsl.core.config.AbstractTemplateProps;
import org.modsl.core.layout.AbstractLayout;
import org.modsl.core.layout.AbstractMetricsAdjustment;
import org.modsl.core.model.diagram.Diagram;
import org.modsl.core.svg.AbstractSvgWriter;

/**
 * Core process, aggregates parsing, layout and rendering.
 * 
 * @author avishnyakov
 * 
 */
public abstract class ModslProcessor<LP extends AbstractLayoutProps, TP extends AbstractTemplateProps, D extends Diagram<?, ?, ?>> {

	private final static Logger log = Logger.getLogger(ModslProcessor.class);

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

	protected abstract AbstractBuilder getBuilder();

	protected abstract LP getLayoutProps();

	protected abstract AbstractLayout<D, LP>[] getLayouts();

	protected abstract AbstractMetricsAdjustment<D, TP> getMetrics();

	protected abstract AbstractSvgWriter<D, TP> getSvgWriter();

	protected abstract TP getTemplateProps();

	final public D process(String fileName) {

		try {

			Binding binding = new Binding();
			binding.setVariable("builder", getBuilder());
			new GroovyShell(binding).evaluate(new File(fileName + ".modsl"));
			D diagram = (D) binding.getVariable("diagram");

			getMetrics().apply(diagram);

			for (AbstractLayout<D, LP> layout : getLayouts()) {
				layout.apply(diagram);
			}

			diagram.rescaleToRequestedSize();

			getSvgWriter().renderToFile(diagram, fileName + ".svg");

			return diagram;

		} catch (Exception ex) {
			log.error("Error while processing " + fileName, ex);
		}

		return null;

	}
}
