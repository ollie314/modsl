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

import org.modsl.core.agt.common.FontTransform;
import org.modsl.core.agt.model.Node;
import org.modsl.core.agt.model.Pt;

/**
 * Does simple node size calculation based on node's text height and width.
 * @author avishnyakov
 */
public class SimpleLabelLayout extends AbstractNonConfigurableLayout {

    @Override
    public void apply(Node<?> node) {
        FontTransform ft = node.getType().getConfig().getFontTransform();
        node.setSize(new Pt(ft.getExtStringWidth(node.getName()), ft.getExtHeight(1)));
    }

}
