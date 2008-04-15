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

public class FontSizeTransform {

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

	public FontSizeTransform(String fontName, int fontSize) {

		this.fontName = fontName;
		this.fontSize = fontSize;

		HeadlessCanvas hc = new HeadlessCanvas(fontName, fontSize);
		hc.paint(null);
		this.fontMetrics = hc.fontMetrics;

	}

	public String getFontName() {
		return fontName;
	}

	public int getFontSize() {
		return fontSize;
	}

	public int getHeight() {
		return fontMetrics.getHeight();
	}

	public int getLeftLeading() {
		return 3 + fontSize / 5;
	}

	public int getRightTrailing() {
		return 3 + fontSize / 5;
	}

	public int getBaseline() {
		return fontMetrics.getAscent();
	}

	/**
	 * @param index -
	 *            line number
	 * @return baseline of the nums line in multiline text
	 */
	public int getExtBaseline(int index) {
		return getExtPosition(index) + getBaseline();
	}

	/**
	 * @param index -
	 *            line number (starting with 0)
	 * @return relative position of the nums line in multiline text
	 */
	public int getExtPosition(int index) {
		return getTopLeading() + index * getHeight();
	}

	/**
	 * @param num -
	 *            number of lines (starting with 1)
	 * @return total height of multi line text -- leading+height*num+trailing
	 */
	public int getExtHeight(int num) {
		return getTopLeading() + num * getHeight() + getBottomTrailing();
	}

	public int getUnderline() {
		return getBaseline() + 2;
	}

	public int getExtUnderline(int index) {
		return getExtBaseline(index) + 2;
	}

	public int getStringWidth(String str) {
		return fontMetrics.stringWidth(str);
	}

	public int getTopLeading() {
		return fontMetrics.getLeading() + 2;
	}

	public int getBottomTrailing() {
		return getTopLeading();
	}

	public int getExtStringWidth(String str) {
		return getLeftLeading() + getStringWidth(str) + getRightTrailing();
	}

}
