package org.modsl.core.agt.model;

public class Bend implements Point {

	protected int index;
	protected Pt pos;
	protected Pt size = new Pt(0d, 0d);

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

}
