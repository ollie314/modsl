package org.modsl.core.agt.model;

public abstract class AbstractBox extends AbstractElement {

    protected Pt pos = new Pt();
    protected Pt altPos = new Pt();
    protected Pt size = new Pt();

    public AbstractBox(MetaType type) {
        super(type);
    }

    public AbstractBox(MetaType type, String name) {
        super(type, name);
    }

    public AbstractBox() {
        
        //
    }

    /**
     * @return center position (will be different from pos if size > 0)
     */
    public Pt getCtrPos() {
        return pos;
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
