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

package org.modsl.core.agt.layout.fr;

import org.modsl.core.agt.model.Pt;

public class Bar {

	int index;
	Pt pos;
	boolean vertical;

	public Bar(boolean vertical, int index, double pos) {
		this.vertical = vertical;
		this.index = index;
		this.pos = vertical ? new Pt(0d, pos) : new Pt(pos, 0d);
	}

	public int getIndex() {
		return index;
	}

	public Pt getPos() {
		return pos;
	}

	public boolean isVertical() {
		return vertical;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setPos(Pt pos) {
		this.pos = pos;
	}

	public void setVertical(boolean vertical) {
		this.vertical = vertical;
	}

}
