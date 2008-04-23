package org.modsl.core.agt;

import org.apache.log4j.Logger;

/**
 * Abstract graph tree element
 * 
 * @author avishnyakov
 *
 */
public abstract class AbstractGraphElement<T extends AGTType> {

    protected Logger log = Logger.getLogger(getClass());

    /**
     * Name of this object
     */
    protected String name;

    /**
     * Parent node
     */
    protected Node<T> parent;

    /**
     * This element is visible
     */
    protected boolean visible = true;

    /**
     * Type
     */
    protected T type;

    public AbstractGraphElement(T type) {
        super();
        this.type = type;
    }

    public AbstractGraphElement(T type, String name) {
        super();
        this.type = type;
        this.name = name;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @return parent
     */
    public Node<T> getParent() {
        return parent;
    }

    public T getType() {
        return type;
    }

    /**
     * @return true if visible
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Guaranteed to be called on all elements of the graph, traversing depth first, nodes before edges
     * when elements of the graph already created. Allows post-processing to resolve forward references etc.
     */
    public abstract void postCreate();

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
    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    public void setType(T type) {
        this.type = type;
    }

    /**
     * Set visible flag
     * @param visible
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

}