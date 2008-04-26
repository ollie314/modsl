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

package org.modsl.core.cfg;

import org.modsl.core.agt.layout.Layout;
import org.modsl.core.agt.model.MetaType;

public abstract class ConfigLoader {

	protected Class<? extends MetaType> metaTypeClass;
	protected String path, name;

	public ConfigLoader(String path, String name, Class<? extends MetaType> metaTypeClass) {
		this.path = path;
		this.name = name;
		this.metaTypeClass = metaTypeClass;
	}

	public void load() {
		initLayouts();
		for (MetaType mt : metaTypeClass.getEnumConstants()) {
			for (Layout l : mt.getConfig().getLayouts()) {
				if (l.getConfigName() != null) {
					PropLoader pl = new PropLoader(path, l.getConfigName(), true);
					l.setLayoutConfig(pl.getProps());
				}
			}
	        new FontTransformLoader(path, name, metaTypeClass).load();
		}
	}
	
	public abstract void initLayouts();

}
