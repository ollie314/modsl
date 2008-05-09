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

package org.modsl.core.agt.common;

import org.modsl.core.agt.decor.AbstractDecorator;

public class MetaTypeConfig {

    protected FontTransform fontTransform;
    protected AbstractDecorator<?> decorator;

    public AbstractDecorator<?> getDecorator() {
        return decorator;
    }

    /**
     * @return font transform object for this meta type
     */
    public FontTransform getFontTransform() {
        return fontTransform;
    }

    /**
     * Alias to be used in templates
     * @see MetaTypeConfig#getFontTransform()
     * @return font transform
     */
    public FontTransform getFt() {
        return fontTransform;
    }

    public void setDecorator(AbstractDecorator<?> decorator) {
        this.decorator = decorator;
    }

    /**
     * @param ft set font transform object for this meta type
     */
    public void setFontTransform(FontTransform fontTransform) {
        this.fontTransform = fontTransform;
    }

}
