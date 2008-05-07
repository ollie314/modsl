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
