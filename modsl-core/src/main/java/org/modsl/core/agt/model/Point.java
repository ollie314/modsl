package org.modsl.core.agt.model;

public interface Point {

	/**
	 * @return position (top left corner for objects w/ size > 0)
	 */
	public Pt getPos();

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

}
