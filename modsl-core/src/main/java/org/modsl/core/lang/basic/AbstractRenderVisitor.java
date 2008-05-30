package org.modsl.core.lang.basic;

import java.awt.Graphics;

import org.modsl.core.agt.visitor.AbstractMetaTypeVisitor;

public abstract class AbstractRenderVisitor extends AbstractMetaTypeVisitor {

    protected Graphics g;

    public AbstractRenderVisitor graphics(Graphics g) {
        this.g = g;
        return this;
    }

}
