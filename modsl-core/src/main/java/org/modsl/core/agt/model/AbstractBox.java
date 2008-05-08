package org.modsl.core.agt.model;

public abstract class AbstractBox extends AbstractGraphElement {

    protected int index = -1;
    protected Pt pos = new Pt();
    protected Pt size = new Pt();

    public AbstractBox(MetaType type) {
        super(type);
    }

    /**
     * @return center position (will be different from pos if size > 0)
     */
    public Pt getCtrPos() {
        return pos;
    }

    public int getIndex() {
        return index;
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

    public abstract boolean isConnectedTo(Point point);

    public abstract boolean isVirtual();

    public void setIndex(int index) {
        this.index = index;
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
