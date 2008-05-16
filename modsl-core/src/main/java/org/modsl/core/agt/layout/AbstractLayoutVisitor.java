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

package org.modsl.core.agt.layout;

import java.util.Map;

import org.apache.log4j.Logger;
import org.modsl.core.agt.model.Bend;
import org.modsl.core.agt.model.Edge;
import org.modsl.core.agt.model.EdgeLabel;
import org.modsl.core.agt.model.Graph;
import org.modsl.core.agt.model.MetaType;
import org.modsl.core.agt.model.Node;
import org.modsl.core.agt.model.NodeLabel;
import org.modsl.core.agt.visitor.AbstractVisitor;

/**
 * Base class for all layout visitors
 * @author AVishnyakov
 */
public abstract class AbstractLayoutVisitor extends AbstractVisitor {

    protected Logger log = Logger.getLogger(getClass());

    protected MetaType type;

    /**
     * New visitor for given meta-type
     * @param type
     */
    public AbstractLayoutVisitor(MetaType type) {
        this.type = type;
    }

    public void apply(Bend bend) {
        // to be overriden
    }

    public void apply(Edge bend) {
        // to be overriden
    }

    public void apply(Graph bend) {
        // to be overriden
    }

    public void apply(NodeLabel bend) {
        // to be overriden
    }

    public void apply(EdgeLabel bend) {
        // to be overriden
    }

    public void apply(Node bend) {
        // to be overriden
    }

    /**
     * @return configuration name (used to initialize this layout visitor)
     */
    public abstract String getConfigName();

    @Override
    public void in(Bend bend) {
        if (type.equals(bend.getType())) {
            apply(bend);
        }
    }

    @Override
    public void in(Edge edge) {
        if (type.equals(edge.getType())) {
            apply(edge);
        }
    }

    @Override
    public void in(Graph graph) {
        if (type.equals(graph.getType())) {
            apply(graph);
        }
    }

    @Override
    public void in(NodeLabel label) {
        if (type.equals(label.getType())) {
            apply(label);
        }
    }

    @Override
    public void in(EdgeLabel label) {
        if (type.equals(label.getType())) {
            apply(label);
        }
    }

    @Override
    public void in(Node node) {
        if (type.equals(node.getType())) {
            apply(node);
        }
    }

    /**
     * Called when the configuration map is loaded from disk
     * @param propMap
     */
    public abstract void setLayoutConfig(Map<String, String> propMap);

}
