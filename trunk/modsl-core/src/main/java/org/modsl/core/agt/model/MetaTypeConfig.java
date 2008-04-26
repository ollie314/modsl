package org.modsl.core.agt.model;

import org.modsl.core.agt.layout.Layout;

public class MetaTypeConfig {

	protected FontTransform fontTransform;
	protected Layout[] layouts;

	public MetaTypeConfig() {
		this.layouts = new Layout[] {};
	}

	public MetaTypeConfig(Layout[] layouts) {
		this.layouts = layouts;
	}

	/**
	 * @return font transform object for this meta type
	 */
	public FontTransform getFontTransform() {
		return fontTransform;
	}

	/**
	 * @return array of layouts for this meta type
	 */
	public Layout[] getLayouts() {
		return layouts;
	}

	/**
	 * @param ft set font transform object for this meta type
	 */
	public void setFontTransform(FontTransform fontTransform) {
		this.fontTransform = fontTransform;
	}

	/**
	 * Set layout array for this meta type
	 * @param layouts
	 */
	public void setLayout(Layout[] layouts) {
		this.layouts = layouts;
	}

}
