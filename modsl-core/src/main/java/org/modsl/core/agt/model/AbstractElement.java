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

package org.modsl.core.agt.model;

import org.apache.log4j.Logger;
import org.modsl.core.agt.visitor.AbstractVisitor;

/**
 * Abstract graph element
 * @author avishnyakov
 */
public abstract class AbstractElement {

    protected Logger log = Logger.getLogger(getClass());

    /**
     * Name of this object
     */
    protected String name;

    /**
     * Parent node
     */
    protected AbstractElement parent;

    /**
     * This element is visible
     */
    protected boolean visible = true;

    /**
     * Type
     */
    protected MetaType type;

    protected int index = -1;

    /**
     * Create new
     * @param type
     */
    public AbstractElement(MetaType type) {
        super();
        this.type = type;
    }

    /**
     * Create new
     * @param type
     * @param name
     */
    public AbstractElement(MetaType type, String name) {
        super();
        this.type = type;
        this.name = name;
    }

    /**
     * Guaranteed to be called on all elements of the graph, traversing depth
     * first, edges before nodes when elements of the graph already created.
     */
    public abstract void accept(AbstractVisitor visitor);

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @return parent
     */
    public AbstractElement getParent() {
        return parent;
    }

    /**
     * @return meta type
     */
    public MetaType getType() {
        return type;
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
    public void setParent(AbstractElement parent) {
        this.parent = parent;
    }

    /**
     * Set meta type
     * @param type
     */
    public void setType(MetaType type) {
        this.type = type;
    }

    /**
     * Set visible flag
     * @param visible
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getIndex() {
        return index;
    }

    public boolean isVirtual() {
        return false;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}