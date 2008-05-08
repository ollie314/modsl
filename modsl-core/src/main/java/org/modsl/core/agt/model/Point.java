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

public interface Point {

	/**
	 * @return position (top left corner for objects w/ size > 0)
	 */
	public Pt getPos();

	/**
	 * @return center position (will be different from pos if size > 0)
	 */
	public Pt getCtrPos();
	
	/**
	 * Set position (top left corner)
	 * @param pos
	 */
	public void setPos(Pt point);

	/**
	 * @return size
	 */
	public Pt getSize();

	/**
	 * Set size
	 * @param size
	 */
	public void setSize(Pt size);

	public int getIndex();

	public void setIndex(int index);
	
	public boolean isVirtual();
	
	public boolean isConnectedTo(Point point);

}
