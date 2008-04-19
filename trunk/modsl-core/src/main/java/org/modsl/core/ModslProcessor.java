package org.modsl.core;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

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

	final public D process(File in) throws FileNotFoundException {
		return process(new FileInputStream(in).toString());
	}

	protected D _process(String script, File file) {

		try {

			Binding binding = new Binding();
			binding.setVariable("builder", getBuilder());
			GroovyShell shell = new GroovyShell(binding);
			if (script != null) {
				shell.evaluate(script);
			} else {
				shell.evaluate(file);
			}
			D diagram = (D) binding.getVariable("diagram");

			getMetrics().apply(diagram);

			for (AbstractLayout<D, LP> layout : getLayouts()) {
				layout.apply(diagram);
			}

			diagram.rescaleToRequestedSize();

			getSvgWriter().render(diagram);

			return diagram;

		} catch (Exception ex) {
			log.error("Error while processing diagram script " + script, ex);
		}

		return null;

	}

	final public D process(String script) {
		return _process(script, null);
	}

	final public D process(File in, File out) throws FileNotFoundException {
		D d = _process(null, in);
		PrintStream p = new PrintStream(new FileOutputStream(out));
		p.print(d.getOutput());
		p.close();
		return d;
	}

	final public D process(String script, File out) throws FileNotFoundException {
		D d = _process(script, null);
		PrintStream p = new PrintStream(new FileOutputStream(out));
		p.print(d.getOutput());
		p.close();
		return d;
	}

}
