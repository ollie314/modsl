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

package org.modsl.core.lang.basic;

import org.modsl.core.agt.layout.Layout;
import org.modsl.core.agt.layout.SimpleLabelLayout;
import org.modsl.core.agt.model.FontTransform;
import org.modsl.core.agt.model.MetaType;

/**
 * Basic model element meta-types
 * @author AVishnyakov
 */
public enum BasicType implements MetaType {

    GRAPH, NODE(new Layout[] { new SimpleLabelLayout() }), EDGE;

    protected FontTransform fontTransform;
    protected Layout[] layouts;

    private BasicType() {
        this.layouts = new Layout[] {};
    }

    private BasicType(Layout[] layouts) {
        this.layouts = layouts;
    }

    @Override
    public FontTransform getFontTransform() {
        return fontTransform;
    }

    @Override
    public void setFontTransform(FontTransform fontTransform) {
        this.fontTransform = fontTransform;
    }

    @Override
    public Layout[] getLayouts() {
        return layouts;
    }

}
