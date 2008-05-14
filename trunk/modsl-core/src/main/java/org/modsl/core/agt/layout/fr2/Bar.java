package org.modsl.core.agt.layout.fr2;

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
