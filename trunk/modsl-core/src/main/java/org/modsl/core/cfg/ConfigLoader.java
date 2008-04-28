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

/**
 * Base class for all configuration loaders. Loads DSL configuration from given
 * path (colon separated). Populates meta type for that DSL with font
 * transformation info and inits all layout manager classes.
 * @author AVishnyakov
 */
public abstract class ConfigLoader {

	/**
	 * Parent meta type class to be filled with configuration information
	 */
	protected Class<? extends MetaType> metaTypeClass;
	protected String path, name;

	/**
	 * Create new
	 * @param path
	 * @param name
	 * @param metaTypeClass
	 */
	public ConfigLoader(String path, String name, Class<? extends MetaType> metaTypeClass) {
		this.path = path;
		this.name = name;
		this.metaTypeClass = metaTypeClass;
	}

	/**
	 * Load configuration from disk
	 */
	public void load() {
		initLayouts();
		for (MetaType mt : metaTypeClass.getEnumConstants()) {
			for (Layout l : mt.getConfig().getLayouts()) {
				if (l.getConfigName() != null) {
					PropLoader pl = new PropLoader(path, l.getConfigName(), false);
					pl.load();
					l.setLayoutConfig(pl.getProps());
				}
			}
			new FontTransformLoader(path, name, metaTypeClass).load();
		}
	}

	/**
	 * Initialize layout classes. Subclasses of this class need to register
	 * layout manager class arrays with corresponding meta type class instances
	 * here.
	 */
	public abstract void initLayouts();

}
