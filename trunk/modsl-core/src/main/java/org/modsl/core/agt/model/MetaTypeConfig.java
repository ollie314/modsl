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

package org.modsl.core.agt.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.modsl.core.agt.layout.Layout;

public class MetaTypeConfig {

	protected FontTransform fontTransform;
	protected Layout[] layouts;
	protected Map<String, String> styles = new HashMap<String, String>();
	protected MetaType type;

	public MetaTypeConfig(MetaType type) {
		this(type, new Layout[] {});
	}

	public MetaTypeConfig(MetaType type, Layout[] layouts) {
		this.type = type;
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

	/**
	 * Add new style to the map
	 * @param key
	 * @param value
	 */
	public void addStyle(String key, String value) {
		styles.put(key, value);
	}

	/**
	 * @return styles
	 */
	public Map<String, String> getStyles() {
		return styles;
	}

	/**
	 * @return styles
	 */
	public List<String> getStylesAsList() {
		List<String> lst = new LinkedList<String>();
		for (Map.Entry<String, String> me : styles.entrySet()) {
			lst.add("." + type + "_" + me.getKey() + " { " + me.getValue() + " }");
		}
		return lst;
	}

}
