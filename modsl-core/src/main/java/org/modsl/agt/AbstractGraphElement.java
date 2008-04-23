package org.modsl.agt;

/**
 * Abstract graph tree element
 * 
 * @author avishnyakov
 *
 */
public class AbstractGraphElement {

    /**
     * Name of this object
     */
    protected String name;

    /**
     * Parent node
     */
    protected Node parent;

    /**
     * This element is visible
     */
    protected boolean visible = true;

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @return parent
     */
    public Node getParent() {
        return parent;
    }

    /**
     * @return true if visible
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Set name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set parent
     * @param parent
     */
    public void setParent(Node parent) {
        this.parent = parent;
    }

    /**
     * Set visible flag
     * @param visible
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

}