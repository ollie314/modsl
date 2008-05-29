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

package org.modsl.core.cfg;

import java.awt.Font;

import org.modsl.core.agt.common.FontTransform;
import org.modsl.core.agt.model.MetaType;

public class FontTransformLoader extends PropLoader {

	protected Class<? extends MetaType> metaTypeClass;

	public FontTransformLoader(String path, String name, Class<? extends MetaType> metaTypeClass) {
		super(path, name, true);
		this.metaTypeClass = metaTypeClass;
	}

	public void load() {
		super.load();
		String name = "serif";
		String size = "12";
		int style = 0;
		for (MetaType mt : metaTypeClass.getEnumConstants()) {
			String n = getProp(mt.toString() + ".name");
			if (n == null) {
				n = name;
			} else {
				name = n;
			}
			String s = getProp(mt.toString() + ".size");
			if (s == null) {
				s = size;
			} else {
				size = s;
			}
			String t = getProp(mt.toString() + ".style");
			if (t != null) {
				style = 0;
				t = t.toUpperCase();
				if (t.indexOf("BOLD") > -1) {
					style |= Font.BOLD;
				}
				if (t.indexOf("ITALIC") > -1) {
					style |= Font.ITALIC;
				}
			}
			FontTransform ft = new FontTransform(n, Integer.parseInt(s), style);
			mt.getConfig().setFontTransform(ft);
		}
	}

	public String toString() {
		StringBuilder sb = new StringBuilder(name);
		sb.append(" [");
		for (MetaType mt : metaTypeClass.getEnumConstants()) {
			sb.append(mt.toString()).append(":").append(mt.getConfig().getFontTransform()).append(" ");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("]");
		return sb.toString();
	}
}
