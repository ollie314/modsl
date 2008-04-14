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

	public int getXLead() {
		return 3 + fontSize / 5;
	}

	public int getXTrail() {
		return 3 + fontSize / 5;
	}

	public int getYLead() {
		return fontSize / 5;
	}

	public int getYTrail() {
		return 5 + fontSize / 5;
	}

	public int getYGap() {
		return 1 + fontSize / 5;
	}

	public int getYStack(int num) {
		return num > 0 ? (getYLead() + num * getFontSize() + (num - 1) * getYGap() + getYTrail()) : 0;
	}

	public int getFontSize() {
		return fontSize;
	}

	public int getYSize() {
		return getYLead() + getFontSize() + getYTrail();
	}

	public int getYBaseLine() {
		return getYLead() + getFontSize();
	}

	public String getFontName() {
		return fontName;
	}

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

	public FontMetrics getFontMetrics() {
		return fontMetrics;
	}

}
