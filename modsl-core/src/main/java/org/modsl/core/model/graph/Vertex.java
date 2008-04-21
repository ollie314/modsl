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

package org.modsl.core.model.graph;

import java.util.List;

import org.modsl.core.model.XY;

/**
 * Represents a vertex for the layout algorithm
 * 
 * @author avishnyakov
 * 
 */
public interface Vertex {

    /**
     * @return add current position to history
     */
    public void addPositionToHistory();

    /**
     * @return alternate (future) position, used as temp storage in algorithms
     */
    public XY getAltPosition();

    /**
     * @return current position (center)
     */
    public XY getCenterPosition();

    /**
     * @return diagonal
     */
    public double getDiagonal();

    /**
     * @return history of this vertex's positions (useful for debugging of layout algorithms)
     */
    public List<XY> getPosHistory();

    /**
     * @return current position (top left)
     */
    public XY getPosition();

    /**
     * @return dimensions (width, height) of this vertex 
     */
    public XY getSize();

    /**
     * @return "weight" of this vertex
     */
    public double getWeight();

    /**
     * Set vertex position 
     * @param position
     */
    public void setPosition(XY position);

    /**
     * Set new size
     * @param size
     */
    public void setSize(XY size);

    /**
     * Set the new weight (lighter vertexes will be positioned closer to the top).
     * Should be normalized to 0..1.
     * @param weight
     */
    public void setWeight(double weight);
    
    /**
    * @return tan() of the angle between this vertex's diagonal and horizontal line
    */
    public double tan();

}