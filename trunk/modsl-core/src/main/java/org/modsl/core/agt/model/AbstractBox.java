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

package org.modsl.core.agt.model;

/**
 * Abstract rectangle graph element
 * @author avishnyakov
 */
public abstract class AbstractBox extends AbstractElement {

    /**
     * Position
     */
    protected Pt pos = new Pt();
    
    /**
     * Alst position (needed for layouts)
     */
    protected Pt altPos = new Pt();
    
    /**
     * Size
     */
    protected Pt size = new Pt();

    /**
     * Create new
     */
    public AbstractBox() {
        //
    }

    /**
     * Create new
     * @param type
     */
    public AbstractBox(MetaType type) {
        super(type);
    }

    /**
     * Create new
     * @param type
     * @param name
     */
    public AbstractBox(MetaType type, String name) {
        super(type, name);
    }

    /**
     * @return center position (will be different from pos if size > 0)
     */
    public Pt getCtrPos() {
        return pos;
    }

    /**
     * @return position (top left corner for objects w/ size > 0)
     */
    public Pt getPos() {
        return pos;
    }

    /**
     * @return size
     */
    public Pt getSize() {
        return size;
    }

    /**
     * Set position (top left corner)
     * @param pos
     */
    public void setPos(Pt point) {
        this.pos = point;
    }

    /**
     * Set size
     * @param size
     */
    public void setSize(Pt size) {
        this.size = size;
    }

}
