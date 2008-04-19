package org.modsl.core;

import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.modsl.cls.ClassDiagramProcessor;
import org.modsl.collab.CollabDiagramProcessor;
import org.modsl.core.config.AbstractLayoutProps;
import org.modsl.core.config.AbstractTemplateProps;
import org.modsl.core.model.diagram.Diagram;

/**
 * Core process, aggregates parsing, layout and rendering.
 * 
 * @author avishnyakov
 * 
 */
public abstract class ModslProcessor<LP extends AbstractLayoutProps, TP extends AbstractTemplateProps, D extends Diagram<?, ?, ?>> {

	private final static Logger log = Logger.getLogger(ModslProcessor.class);
	protected static final String[] scriptRoots = new String[] { "./target/classes/samples/cls",  "./target/classes/samples/collab"};
	protected static GroovyScriptEngine scriptEngine;

	protected static String path = "/config";

	protected static ClassDiagramProcessor classDiagramProcessor;
	protected static CollabDiagramProcessor collabDiagramProcessor;

	public static ClassDiagramProcessor getClassDiagramProcessor() {
		return classDiagramProcessor;
	}

	public static CollabDiagramProcessor getCollabDiagramProcessor() {
		return collabDiagramProcessor;
	}

	public static void init() {

		try {
			scriptEngine = new GroovyScriptEngine(scriptRoots);
		} catch (IOException ex) {
			log.error("Failed to initialize Groovy script engine", ex);
		}

		classDiagramProcessor = new ClassDiagramProcessor(path);
		collabDiagramProcessor = new CollabDiagramProcessor(path);

	}

	public static void init(String p) {
		path = p;
		init();
	}

	protected LP layoutProps;
	protected TP templateProps;

	protected abstract void layout(D diagram);

	protected abstract void metrics(D diagram);

	protected abstract D parse(String fileName) throws ResourceException, ScriptException;

	final public D process(String fileName) {
		try {
			D diagram = parse(fileName);
			metrics(diagram);
			layout(diagram);
			diagram.rescaleToRequestedSize();
			render(diagram);
			return diagram;
		} catch (Exception ex) {
			log.error("Error while processing " + fileName, ex);
		}
		return null;
	}

	protected abstract void render(D diagram) throws FileNotFoundException;

}
