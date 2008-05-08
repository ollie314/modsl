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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

/**
 * Base class for all property loaders
 * @author avishnyakov
 */
public class PropLoader {

	protected final Logger log = Logger.getLogger(getClass());

	protected Map<String, String> props = new HashMap<String, String>();
	protected List<String> dirs = new ArrayList<String>();

	protected String name;
	protected boolean optional;

	/**
	 * Create new
	 * @param path
	 * @param name
	 */
	public PropLoader(String path, String name, boolean optional) {
		this.name = name;
		this.optional = optional;
		tokenizePath(path);
	}

	/**
	 * @param key
	 * @return booolean property
	 */
	protected boolean getBooleanProp(String key) {
		return Boolean.parseBoolean(getMandatoryProp(key));
	}

	/**
	 * @param key
	 * @return double property
	 */
	protected double getDoubleProp(String key) {
		return Double.parseDouble(getMandatoryProp(key));
	}

	/**
	 * @param key
	 * @return int property
	 */
	protected int getIntegerProp(String key) {
		return Integer.parseInt(getMandatoryProp(key));
	}

	/**
	 * @param key
	 * @return String property
	 */
	protected String getMandatoryProp(String key) {
		String v = getProp(key);
		if (v == null) {
			throw new ConfigException("Configuration property " + key + " not found @ path=" + dirs + ", name=" + name);
		}
		return v;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return path
	 */
	public String getPath() {
		return dirs.toString();
	}

	/**
	 * @param key
	 * @return String property
	 */
	protected String getProp(String key) {
		String v = props.get(key);
		if (v != null) {
			v.trim();
		}
		return v;
	}

	/**
	 * @return map of all properties
	 */
	public Map<String, String> getProps() {
		return props;
	}

	/**
	 * Load property set from given path for specific diagram name
	 */
	public void load() {
		for (String dir : dirs) {
			loadProps(dir + "/" + name + ".properties");
		}
		if (!optional && props.size() == 0) {
			throw new ConfigException("Property set is empty for path=" + dirs + ", name=" + name);
		}
	}

	/**
	 * Load properties
	 * @param name file name
	 * @throws IOException
	 */
	private void loadProps(String name) {
		try {
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			InputStream is = cl.getResourceAsStream(name);
			if (is == null) {
				cl = this.getClass().getClassLoader();
				is = cl.getResourceAsStream(name);
			}
			if (is == null) {
				return;
			}
			BufferedReader r = new BufferedReader(new InputStreamReader(is));
			Properties p = new Properties();
			p.load(r);
			r.close();
			for (Map.Entry<Object, Object> me : p.entrySet()) {
				props.put((String) me.getKey(), (String) me.getValue());
			}
		} catch (IOException ex) {
			log.debug("Got exception when loading properties from " + name, ex);
		}
	}

	/**
	 * Will tokenize colon-delimited path into a list of dirs
	 * @param path
	 */
	private void tokenizePath(String path) {
		StringTokenizer tokenizer = new StringTokenizer(path, ":", false);
		while (tokenizer.hasMoreElements()) {
			dirs.add((String) tokenizer.nextElement());
		}
	}

}