package org.modsl.core.agt.render;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.modsl.core.agt.visitor.AbstractMetaTypeVisitor;

public abstract class AbstractRenderVisitor extends AbstractMetaTypeVisitor {

    protected Graphics2D g;
    protected BufferedImage img;
    protected int width, height;

    public AbstractRenderVisitor image(BufferedImage img) {
        this.img = img;
        this.g = (Graphics2D) img.getGraphics();
        this.width = img.getWidth();
        this.height = img.getHeight();
        return this;
    }
}
