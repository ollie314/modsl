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

public class Bend implements Point {

	protected int index = -1;
	protected Pt pos = new Pt();
	protected Pt size = new Pt();
	protected Edge<?> parent;

	@Override
	public int getIndex() {
		return index;
	}

	@Override
	public Pt getPos() {
		return pos;
	}

	@Override
	public Pt getSize() {
		return size;
	}

	@Override
	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public void setPos(Pt point) {
		this.pos = point;
	}

	@Override
	public void setSize(Pt size) {
		this.size = size;
	}

	@Override
	public boolean isVirtual() {
		return true;
	}

	@Override
	public boolean isConnectedTo(Point p2) {
		if (parent.getDistance(this, p2) == 1) {
			return true;
		}
		return false;
	}

	public Edge<?> getParent() {
		return parent;
	}

	public void setParent(Edge<?> parent) {
		this.parent = parent;
	}

	@Override
	public Pt getCtrPos() {
		return pos;
	}

	// TODO equals, hashcode

}
