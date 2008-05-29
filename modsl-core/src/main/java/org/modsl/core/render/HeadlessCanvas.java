package org.modsl.core.render;

import java.awt.Canvas;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import org.apache.log4j.Logger;

/**
 * This class is used to get access to font metrics in headless (servlet)
 * environment
 * 
 * @author avishnyakov
 */
public class HeadlessCanvas extends Canvas {

    static final long serialVersionUID = 1L;
    static HeadlessCanvas singleton = new HeadlessCanvas();

    static {
        System.setProperty("java.awt.headless", "true");
    }

    public static synchronized FontMetrics getMetrics(String name, int size, int style) {
        singleton.name = name;
        singleton.size = size;
        singleton.style = style;
        singleton.paint(null);
        return singleton.fontMetrics;
    }

    Logger log = Logger.getLogger(getClass());
    
    String name;
    int size;
    int style;

    FontMetrics fontMetrics;

    public void paint(Graphics g) {
        fontMetrics = getFontMetrics(new Font(name, style, size));
        if (log.isDebugEnabled()) {
            log.debug(this.fontMetrics);
        }
    }

}
