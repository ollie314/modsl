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

package org.modsl.cls.factory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.modsl.cls.model.ClassElement;
import org.modsl.cls.model.ClassElementDetail;
import org.modsl.cls.model.ClassElementDetailScope;
import org.modsl.cls.model.ClassElementDetailType;
import org.modsl.utils.Utils;

/**
 * Creates class diagram elements details from the given Groovy script input
 * 
 * @author avishnyakov
 *
 */
public class ClassElementDetailFactory extends ClassAbstractFactory {

    protected Map<String, Object> typeMap, scopeMap;

    public ClassElementDetailFactory() {
        metaKeys = Arrays.asList(new String[] { "_attribute", "_static_attribute", "_method", "_static_method" });
        typeMap = Utils.toMap(new Object[] { "_attribute", ClassElementDetailType.ATTRIBUTE, "_static_attribute",
                ClassElementDetailType.ATTRIBUTE, "_method", ClassElementDetailType.METHOD, "_static_method",
                ClassElementDetailType.METHOD });
        scopeMap = Utils.toMap(new Object[] { "_attribute", ClassElementDetailScope.INSTANCE, "_static_attribute",
                ClassElementDetailScope.STATIC, "_method", ClassElementDetailScope.INSTANCE, "_static_method",
                ClassElementDetailScope.STATIC });
    }

    public ClassElementDetail build(String metaKey, String value, Object current, Map<String, Object> map) {
        checkParentClass(metaKey, current, ClassElement.class);
        ClassElementDetail ed = new ClassElementDetail(value, (ClassElement) current,
                (ClassElementDetailType) typeMap.get(metaKey), (ClassElementDetailScope) scopeMap.get(metaKey));
        return ed;
    }

    public List<String> getMetaKeys() {
        return metaKeys;
    }

}
