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

import org.modsl.core.agt.visitor.AbstractVisitor;

public class EdgeLabel extends Label<Edge> {

	public static enum Placement {
		MID, ANCHOR1, ANCHOR2;
	}

	AbstractBox<?> anchor1;

	AbstractBox<?> anchor2;

	Pt offset = new Pt(0d, 0d);

	Placement placement = Placement.MID;

	public EdgeLabel(MetaType type, String name) {
		super(type, name);
	}

	@Override
	public void accept(AbstractVisitor visitor) {
		visitor.in(this);
		visitor.out(this);
	}

	public AbstractBox<?> getAnchor1() {
		return anchor1;
	}

	public AbstractBox<?> getAnchor2() {
		return anchor2;
	}

	public Pt getOffset() {
		return offset;
	}

	public Placement getPlacement() {
		return placement;
	}

	@Override
	public Pt getPos() {
		switch (placement) {
		case MID:
			Pt[] ports = anchor1.getPorts(anchor2);
			Pt mid = ports[0].plus(ports[1].minus(ports[0]).mulBy(0.5d));
			// Pt mid =
			// anchor1.getCtrPos().plus(anchor2.getCtrPos().minus(anchor1.getCtrPos()).mulBy(0.5d));
			mid.incBy(new Pt(offset.y / anchor1.tan(anchor2), offset.y));
			mid.decBy(new Pt(getSize().x / 2d, getSize().y / 2d));
			return mid;
		}
		return null;
	}

	public void setAnchor1(AbstractBox<?> anchor1) {
		this.anchor1 = anchor1;
	}

	public void setAnchor2(AbstractBox<?> anchor2) {
		this.anchor2 = anchor2;
	}

	public void setOffset(double x, double y) {
		this.offset.x = x;
		this.offset.y = y;
	}

	public void setPlacement(Placement placement) {
		this.placement = placement;
	}

}
