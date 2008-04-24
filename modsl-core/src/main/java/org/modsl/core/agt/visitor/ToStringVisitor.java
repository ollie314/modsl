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

package org.modsl.core.agt.visitor;

import org.modsl.core.agt.model.AGTType;
import org.modsl.core.agt.model.Edge;
import org.modsl.core.agt.model.Node;

public class ToStringVisitor<T extends AGTType> extends AbstractVisitor<T> {

    protected StringBuffer sb = new StringBuffer();

    @Override
    public void in(Node<T> node) {
        sb.append(node.toString()).append(" ");
        if (node.getNodes().size() > 0) {
            sb.append("{");
        }
    }

    @Override
    public void out(Node<T> node) {
        if (node.getNodes().size() > 0) {
            sb.append("}");
        }
    }

    @Override
    public void in(Edge<T> edge) {
        sb.append(edge.toString()).append(" ");
    }

    @Override
    public String toString() {
        return sb.toString();
    }

    public String toString(Node<T> root) {
        root.accept(this);
        return toString();
    }

}
