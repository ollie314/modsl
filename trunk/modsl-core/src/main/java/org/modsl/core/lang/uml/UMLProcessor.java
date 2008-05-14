/**
 * Copyright 2008 Andrew Vishnyakov <avishn@gmail.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.modsl.core.lang.uml;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.RecognitionException;
import org.modsl.antlr.uml.UMLLexer;
import org.modsl.antlr.uml.UMLParser;
import org.modsl.core.agt.decor.MetaTypeMapDecorator;
import org.modsl.core.agt.layout.SimpleNodeLabelPosLayoutVisitor;
import org.modsl.core.agt.layout.fr2.FR2LayoutVisitor;
import org.modsl.core.agt.model.Graph;
import org.modsl.core.agt.model.MetaType;
import org.modsl.core.cfg.AbstractProcessor;
import org.modsl.core.lang.uml.decorator.CollabEdgeDecorator;
import org.modsl.core.lang.uml.decorator.CollabNodeDecorator;
import org.modsl.core.lang.uml.layout.CollabEdgeLabelLayout;
import org.modsl.core.lang.uml.layout.CollabNodeLayoutVisitor;
import org.modsl.core.lang.uml.layout.CollabNodeWeightVisitor;

public class UMLProcessor extends AbstractProcessor<UMLParser> {

	@Override
	protected Lexer getLexer(ANTLRStringStream input) {
		return new UMLLexer(input);
	}

	@Override
	protected String getName() {
		return "uml";
	}

	@Override
	protected UMLParser getParser(CommonTokenStream tokens) {
		return new UMLParser(tokens);
	}

	@Override
	protected String getPath() {
		return "cfg/uml:cfg";
	}

	@Override
	protected Graph getGraph() {
		return parser.graph;
	}

	@Override
	protected void runParser() throws RecognitionException {
		parser.collabDiagram();
	}

	@Override
	public void initDecorators() {
		UMLMetaType.COLLAB_GRAPH.getConfig().setDecorator(new MetaTypeMapDecorator(UMLMetaType.class));
		UMLMetaType.COLLAB_NODE_LABEL.getConfig().setDecorator(new CollabNodeDecorator());
		UMLMetaType.COLLAB_EDGE.getConfig().setDecorator(new CollabEdgeDecorator());
	}

	@Override
	public void initLayouts() {
        addLayoutVisitor(new CollabNodeWeightVisitor(UMLMetaType.COLLAB_NODE));
		addLayoutVisitor(new CollabNodeLayoutVisitor(UMLMetaType.COLLAB_NODE));
		// addLayoutVisitor(new
		// SugiyamaLayoutVisitor(UMLMetaType.COLLAB_GRAPH));
		//addLayoutVisitor(new Circle2LayoutVisitor(UMLMetaType.COLLAB_GRAPH));
		addLayoutVisitor(new FR2LayoutVisitor(UMLMetaType.COLLAB_GRAPH));
		addLayoutVisitor(new CollabEdgeLabelLayout(UMLMetaType.COLLAB_EDGE_LABEL));
		addLayoutVisitor(new SimpleNodeLabelPosLayoutVisitor(UMLMetaType.COLLAB_NODE));
	}

	@Override
	protected Class<? extends MetaType> getMetaTypeClass() {
		return UMLMetaType.class;
	}
}
