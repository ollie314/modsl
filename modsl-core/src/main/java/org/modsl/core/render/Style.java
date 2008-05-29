/**
 * Copyright 2008 Andrew Vishnyakov <avishn@gmail.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.modsl.core.render;

import java.awt.Color;
import java.awt.FontMetrics;

import org.apache.log4j.Logger;

/**
 * Encapsulates all diagram style attributes and functions
 * 
 * @author avishnyakov
 */
public class Style implements Cloneable {

    Logger log = Logger.getLogger(getClass());

    FontMetrics fontMetrics;

    String fontName;
    int fontSize;
    int fontStyle;
    Color fontColor;
    
    float lineWidth;
    Color lineColor;
    
    Color fillColor;

    /**
     * @return arrow length for this font size
     */
    public double getArrowLength() {
        return getFontHeight();
    }

    /**
     * @see java.awt.FontMertics#getAscent()
     * @return difference in pixels between the top of the outer box around text
     * and the baseline
     */
    public int getBaseline() {
        return fontMetrics.getAscent();
    }

    /**
     * @return bottom padding in pixels for this font size
     */
    public int getBottomPadding() {
        return getTopPadding();
    }

    /**
     * @param index line number
     * @return baseline of the line <code>index</code> in multi-line text
     */
    public int getExtBaseline(int index) {
        return getExtPosition(index) + getBaseline();
    }

    /**
     * @param num - number of lines (starting with 1)
     * @return total height of multi-line text --
     * <code>padding + height*num + padding</code>
     */
    public int getExtHeight(int num) {
        return getTopPadding() + num * getFontHeight() + getBottomPadding();
    }

    /**
     * @param index line number (starting with 0)
     * @return top (position) of the line <code>index</code> in multi-line
     * text
     */
    public int getExtPosition(int index) {
        return getTopPadding() + index * getFontHeight();
    }

    /**
     * @param str
     * @return string width in pixels if rendered with given fond style and size
     * plus necessary padding on the sides
     */
    public int getExtStringWidth(String str) {
        return getLeftPadding() + getStringWidth(str) + getRightPadding();
    }

    /**
     * @param index line number (starting with 0)
     * @return underline position of the line <code>index</code> in multi-line
     * text
     */
    public int getExtUnderline(int index) {
        return getExtBaseline(index) + 2;
    }

    /**
     * @return fill color
     */
    public Color getFillColor() {
        return fillColor;
    }

    /**
     * @see java.awt.FontMetrics#getHeight()
     * @return font height
     */
    public int getFontHeight() {
        return fontMetrics.getHeight();
    }

    public FontMetrics getFontMetrics() {
        if (fontMetrics == null) {
            fontMetrics = HeadlessCanvas.getMetrics(fontName, fontSize, fontStyle);
        }
        return fontMetrics;
    }

    /**
     * @return font name
     */
    public String getFontName() {
        return fontName;
    }

    /**
     * @return font size
     */
    public int getFontSize() {
        return fontSize;
    }

    public int getFontStyle() {
        return fontStyle;
    }

    /**
     * @return left padding in pixels for this font size
     */
    public int getLeftPadding() {
        return 3 + fontSize / 5;
    }

    /**
     * @return line color
     */
    public Color getLineColor() {
        return lineColor;
    }

    /**
     * @return line width
     */
    public float getLineWidth() {
        return lineWidth;
    }

    /**
     * @return right padding whitespace in pixels for this font size
     */
    public int getRightPadding() {
        return 3 + fontSize / 5;
    }

    /**
     * @param str
     * @return string width in pixels if rendered with given fond style and size
     */
    public int getStringWidth(String str) {
        return fontMetrics.stringWidth(str);
    }

    /**
     * @return top padding in pixels for this font size
     */
    public int getTopPadding() {
        return fontMetrics.getLeading() + 2;
    }

    /**
     * @return position of the underline
     */
    public int getUnderline() {
        return getBaseline() + 2;
    }

    @Override
    public String toString() {
        return fontName + "-" + fontStyle + "." + fontSize;
    }

}
