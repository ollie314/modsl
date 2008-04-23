package org.modsl.agt;

import org.apache.log4j.Logger;

/**
 * Abstract graph tree element
 * 
 * @author avishnyakov
 *
 */
public abstract class AbstractGraphElement {
    
    protected Logger log = Logger.getLogger(getClass());

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
    
    public AbstractGraphElement() {
        super();
    } 

    public AbstractGraphElement(String name) {
        super();
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