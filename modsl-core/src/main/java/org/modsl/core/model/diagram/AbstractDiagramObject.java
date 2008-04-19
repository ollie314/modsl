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

package org.modsl.core.model.diagram;

/**
 * Abstract diagram object superclass
 * 
 * @author avishnyakov
 *
 * @param <P> parent class
 */
public abstract class AbstractDiagramObject<P> {

    /**
     * Name of this object
     */
    protected String name;

    /**
     * Parent object
     */
    protected P parent;

    /**
     * This element is visible
     */
    protected boolean visible = true;

    public AbstractDiagramObject(P parent, String name) {
        super();
        this.parent = parent;
        this.name = name;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @return parent
     */
    public P getParent() {
        return parent;
    }

    /**
     * @return true if visible
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Set name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set parent
     * @param parent
     */
    public void setParent(P parent) {
        this.parent = parent;
    }

    /**
     * Set visible flag
     * @param visible
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

}
