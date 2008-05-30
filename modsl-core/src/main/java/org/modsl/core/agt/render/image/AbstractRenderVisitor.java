package org.modsl.core.agt.render.image;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.modsl.core.agt.visitor.AbstractMetaTypeVisitor;

public abstract class AbstractRenderVisitor extends AbstractMetaTypeVisitor {

    protected Graphics2D g;
    protected int width, height;

    public AbstractRenderVisitor graphics(Graphics2D g, int width, int height) {
        this.g = g;
        this.width = width;
        this.height = height;
        return this;
    }
}
