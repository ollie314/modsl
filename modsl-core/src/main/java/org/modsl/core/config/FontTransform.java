/**
 * Copyright 2008 Andrew Vishnyakov <avishn@gmail.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */

package org.modsl.core.config;

import java.awt.Canvas;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

/**
 * Encapsulates all font metric related attributes and functions
 * 
 * @author avishnyakov
 *
 */
public class FontTransform {

    /**
     * This inner class is used to get access to font metrics in headless environment
     * @author avishnyakov
     *
     */
    private static class HeadlessCanvas extends Canvas {

        private static final long serialVersionUID = 1L;

        private String fontName;
        private int fontSize;

        private FontMetrics fontMetrics;

        public HeadlessCanvas(String fontName, int fontSize) {
            super();
            this.fontName = fontName;
            this.fontSize = fontSize;
        }

        public void paint(Graphics g) {
            this.fontMetrics = getFontMetrics(new Font(fontName, Font.PLAIN, fontSize));
        }

    }

    static {
        System.setProperty("java.awt.headless", "true");
    }

    protected String fontName;
    protected int fontSize;

    protected FontMetrics fontMetrics;

    /**
     * Create new FT given font name and size
     * @param fontName
     * @param fontSize
     */
    public FontTransform(String fontName, int fontSize) {

        this.fontName = fontName;
        this.fontSize = fontSize;

        HeadlessCanvas hc = new HeadlessCanvas(fontName, fontSize);
        hc.paint(null);
        this.fontMetrics = hc.fontMetrics;

    }

    /**
     * @see java.awt.FontMertics#getAscent()
     * @return difference in pixels between the top of the outer box around text and the baseline 
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
     * @param index -
     *            line number
     * @return baseline of the line <code>index</code> in multi-line text
     */
    public int getExtBaseline(int index) {
        return getExtPosition(index) + getBaseline();
    }

    /**
     * @param num -
     *            number of lines (starting with 1)
     * @return total height of multi-line text -- <code>padding + height*num + padding</code>
     */
    public int getExtHeight(int num) {
        return getTopPadding() + num * getHeight() + getBottomPadding();
    }

    /**
     * @param index line number (starting with 0)
     * @return top (position) of the line <code>index</code> in multi-line text
     */
    public int getExtPosition(int index) {
        return getTopPadding() + index * getHeight();
    }

    /**
     * @param str 
     * @return string width in pixels if rendered with given fond style and size plus necessary padding on the sides
     */
    public int getExtStringWidth(String str) {
        return getLeftPadding() + getStringWidth(str) + getRightPadding();
    }

    /**
     * @param index line number (starting with 0)
     * @return underline position of the line <code>index</code> in multi-line text
     */
    public int getExtUnderline(int index) {
        return getExtBaseline(index) + 2;
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

    /**
     * @see java.awt.FontMetrics#getHeight()
     * @return font height
     */
    public int getHeight() {
        return fontMetrics.getHeight();
    }

    /**
     * @return left padding in pixels for this font size
     */
    public int getLeftPadding() {
        return 3 + fontSize / 5;
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

    public double getArrowLength() {
        return getHeight();
    }

}
