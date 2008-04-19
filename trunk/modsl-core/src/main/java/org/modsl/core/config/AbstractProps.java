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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Base class for all property sets
 * 
 * @author avishnyakov
 * 
 */
public abstract class AbstractProps {

	protected final Logger log = Logger.getLogger(getClass());
	protected Map<String, String> props = new HashMap<String, String>();

	protected String path, name;

	public AbstractProps(String path, String name, String fileName) {
		this.path = path;
		this.name = name;
		load(path, name, fileName);
	}

	/**
	 * Load property set from given path for specific diagram name
	 * 
	 * @param path
	 *            root for config set
	 * @param name
	 *            diagram/set name
	 * @param name
	 *            file name
	 */
	private void load(String path, String name, String fileName) {
		this.path = path;
		this.name = name;
		loadProps(props, path + "/" + fileName);
		loadProps(props, path + "/" + name + "/" + fileName);
		if (props.size() == 0) {
			throw new ConfigException("Property set is empty for path=" + path + ", name=" + name + ", file name=" + fileName);
		}
	}

	/**
	 * Load properties
	 * 
	 * @param name
	 *            file name
	 * @throws IOException
	 */
	private void loadProps(Map<String, String> map, String name) {
		try {
			InputStream is = getClass().getResourceAsStream(name);
			if (is == null) {
				return;
			}
			BufferedReader r = new BufferedReader(new InputStreamReader(is));
			Properties p = new Properties();
			p.load(r);
			r.close();
			for (Map.Entry<Object, Object> me : p.entrySet()) {
				map.put((String) me.getKey(), (String) me.getValue());
			}
		} catch (IOException ex) {
			log.debug("Got exception when loading properties from " + name, ex);
		}
	}

	/**
	 * @param key
	 * @return String property
	 */
	protected String getProp(String key) {
		String v = props.get(key);
		if (v == null) {
			throw new ConfigException("Configuration property " + key + " not found @ path=" + path + ", name=" + name);
		}
		return v.trim();
	}

	/**
	 * @param key
	 * @return int property
	 */
	protected int getIntegerProp(String key) {
		return Integer.parseInt(getProp(key));
	}

	/**
	 * @param key
	 * @return double property
	 */
	protected double getDoubleProp(String key) {
		return Double.parseDouble(getProp(key));
	}

	/**
	 * @param key
	 * @return booolean property
	 */
	protected boolean getBooleanProp(String key) {
		return Boolean.parseBoolean(getProp(key));
	}

	/**
	 * @return map of all properties
	 */
	public Map<String, String> getProps() {
		return props;
	}

	public String getPath() {
		return path;
	}

	public String getName() {
		return name;
	}

}
