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

package org.modsl.core.builder;

import java.util.List;
import java.util.Map;

/**
 * Base class for all diagram object's factories
 * 
 * @author avishnyakov
 *
 */
public abstract class AbstractFactory {

    protected List<String> metaKeys;

    public abstract Object build(String metaKey, String value, Object current, Map<String, Object> map);

    public abstract List<String> getMetaKeys();

    /**
     * @param key
     * @param map
     * @return values of the given attribute as String or null if attribute doesn't exist
     */
    public String getStringAttribute(String key, Map<String, Object> map) {
        return (String) map.get(key);
    }

    /**
     * @param key
     * @param map
     * @return values of the given attribute as Integer or null if attribute doesn't exist
     */
    public Integer getIntegerAttribute(String key, Map<String, Object> map) {
        return (Integer) map.get(key);
    }

    /**
     * @param key
     * @param map
     * @return values of the given attribute as int or given default value if attribute doesn't exist
     */
    public int getNullableIntegerAttribute(int defaultValue, String key, Map<String, Object> map) {
        Integer t = getIntegerAttribute(key, map);
        if (t == null) {
            return defaultValue;
        } else {
            return t;
        }
    }

    /**
     * @param key
     * @param map
     * @return values of the given attribute as String or given default value if attribute doesn't exist
     */
    public String getNullableStringAttribute(String defaultValue, String key, Map<String, Object> map) {
        String s = getStringAttribute(key, map);
        if (s == null) {
            return defaultValue;
        } else {
            return s;
        }
    }

    /**
     * @param key
     * @param map
     * @return values of the given attribute as String or throws exception if attribute doesn't exist
     */
    public String getMandatoryStringAttribute(String key, Map<String, Object> map) {
        String s = getStringAttribute(key, map);
        if (s == null) {
            throw new BuilderException("Mandatory attribute " + key + " is missing from the list of attributes " + map);
        } else {
            return s;
        }
    }

}
