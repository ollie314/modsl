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
import org.modsl.core.agt.layout.fr.FRLayoutVisitor;
import org.modsl.core.agt.layout.sugiyama.SugiyamaLayoutVisitor;
import org.modsl.core.agt.model.Graph;
import org.modsl.core.agt.model.MetaType;
import org.modsl.core.cfg.AbstractProcessor;
import org.modsl.core.lang.uml.decorator.cls.ClassAggregationEdgeDecorator;
import org.modsl.core.lang.uml.decorator.cls.ClassExtendsEdgeDecorator;
import org.modsl.core.lang.uml.decorator.cls.ClassImplementsEdgeDecorator;
import org.modsl.core.lang.uml.decorator.cls.ClassNodeDecorator;
import org.modsl.core.lang.uml.decorator.collab.CollabEdgeDecorator;
import org.modsl.core.lang.uml.layout.cls.ClassEdgeLabelLayoutVisitor;
import org.modsl.core.lang.uml.layout.cls.ClassNodeLayoutVisitor;
import org.modsl.core.lang.uml.layout.collab.CollabEdgeLabelLayoutVisitor;
import org.modsl.core.lang.uml.layout.collab.CollabNodeLayoutVisitor;

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
        parser.diagram();
    }

    @Override
    public void initDecorators() {
        initCollabDecorators();
        initClassDecorators();
    }

    private void initClassDecorators() {
        UMLMetaType.CLASS_CLASS_NODE.getConfig().setDecorator(new ClassNodeDecorator());
        UMLMetaType.CLASS_INTERFACE_NODE.getConfig().setDecorator(new ClassNodeDecorator());
        UMLMetaType.CLASS_EXTENDS_EDGE.getConfig().setDecorator(new ClassExtendsEdgeDecorator());
        UMLMetaType.CLASS_IMPLEMENTS_EDGE.getConfig().setDecorator(new ClassImplementsEdgeDecorator());
        UMLMetaType.CLASS_AGGREGATION_EDGE.getConfig().setDecorator(new ClassAggregationEdgeDecorator());
    }

    private void initCollabDecorators() {
        UMLMetaType.COLLAB_EDGE.getConfig().setDecorator(new CollabEdgeDecorator());
    }

    @Override
    public void initLayouts() {
        initCollabLayouts();
        initClassLayouts();
    }

    private void initClassLayouts() {
        addLayoutVisitor(new ClassNodeLayoutVisitor(UMLMetaType.CLASS_CLASS_NODE));
        addLayoutVisitor(new ClassNodeLayoutVisitor(UMLMetaType.CLASS_INTERFACE_NODE));
        //addLayoutVisitor(new SugiyamaLayoutVisitor(UMLMetaType.CLASS_GRAPH));
        addLayoutVisitor(new FRLayoutVisitor(UMLMetaType.CLASS_GRAPH));
        addLayoutVisitor(new ClassEdgeLabelLayoutVisitor(UMLMetaType.CLASS_AGGREGATION_EDGE));
    }

    private void initCollabLayouts() {
        // addLayoutVisitor(new
        // CollabNodeWeightVisitor(UMLMetaType.COLLAB_NODE));
        addLayoutVisitor(new CollabNodeLayoutVisitor(UMLMetaType.COLLAB_NODE));
        addLayoutVisitor(new SugiyamaLayoutVisitor(UMLMetaType.COLLAB_GRAPH));
        // addLayoutVisitor(new FRLayoutVisitor(UMLMetaType.COLLAB_GRAPH));
        addLayoutVisitor(new CollabEdgeLabelLayoutVisitor(UMLMetaType.COLLAB_GRAPH));
    }

    @Override
    protected Class<? extends MetaType> getMetaTypeClass() {
        return UMLMetaType.class;
    }
}
