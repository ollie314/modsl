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

/**
 * Base class for all diagram configuration sets
 * 
 * @author avishnyakov
 *
 * @param <F> font size transformer class
 */
public abstract class AbstractConfig<F extends FontTransform> {

    protected static final String PROPS_FILE_NAME = "template.properties";

    protected Map<String, String> props = new HashMap<String, String>();
    protected String path;

    /**
     * New configuration at given path
     * @param path
     */
    public AbstractConfig(String path) {
        this.path = path;
        try {
            loadProps(path + PROPS_FILE_NAME);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Load properties
     * @param name file name
     * @throws IOException
     */
    protected void loadProps(String name) throws IOException {
        InputStream is = getClass().getResourceAsStream(name);
        if (is == null) {
            throw new RuntimeException("Cannot find configuration file " + name);
        }
        BufferedReader r = new BufferedReader(new InputStreamReader(is));
        Properties p = new Properties();
        p.load(r);
        r.close();
        for (Map.Entry<Object, Object> me : p.entrySet()) {
            props.put((String) me.getKey(), (String) me.getValue());
        }
    }

    /**
     * @param key
     * @return String property
     */
    protected String getProp(String key) {
        return props.get(key).trim();
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
     * @return location of the configuration directory
     */
    public String getPath() {
        return path;
    }

    /**
     * @return map of all properties
     */
    public Map<String, String> getProps() {
        return props;
    }

}
