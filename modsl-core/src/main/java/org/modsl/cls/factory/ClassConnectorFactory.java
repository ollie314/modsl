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

import org.modsl.cls.model.ClassConnector;
import org.modsl.cls.model.ClassElement;
import org.modsl.core.builder.BuilderException;
import org.modsl.utils.Utils;

/**
 * Creates class diagram connectors from the given Groovy script input
 * 
 * @author avishnyakov
 *
 */
public class ClassConnectorFactory extends ClassAbstractFactory {

    protected Map<String, Object> typeMap;

    public ClassConnectorFactory() {
        metaKeys = Arrays.asList(new String[] { "_extends", "_implements", "_association" });
        typeMap = Utils.toMap(new Object[] { "_extends", ClassConnector.Type.EXTENDS, "_implements",
                ClassConnector.Type.IMPLEMENTS, "_association", ClassConnector.Type.ASSOCIATION });
    }

    public ClassConnector build(String metaKey, String value, Object current, Map<String, Object> map) {
        checkParentClass(metaKey, current, ClassElement.class);
        ClassElement se = (ClassElement) current;
        ClassConnector c = new ClassConnector(se.getParent());
        c.setStartElement(se);
        if (value == null) {
            c.setEndElementName(getMandatoryStringAttribute("_to", map));
        } else {
            c.setEndElementName(value);
        }
        ClassConnector.Type type = (ClassConnector.Type) typeMap.get(metaKey);
        c.setType(type == null ? ClassConnector.Type.ASSOCIATION : type);
        c.setStartMultiplicity(getNullableStringAttribute(null, "_multi_from", map));
        c.setEndMultiplicity(getNullableStringAttribute(null, "_multi_to", map));
        return c;
    }

    public List<String> getMetaKeys() {
        return metaKeys;
    }

    public static void finalizeConnector(ClassConnector connector) {
        if (connector.getEndElement() == null) {
            if (connector.getEndElementName() == null) {
                throw new BuilderException("Connector end element is undefined " + connector);
            }
            ClassElement element = connector.getParent().getElement(connector.getEndElementName());
            if (element == null) {
                throw new BuilderException("Cannot find end element '" + connector.getEndElementName() + "' for connector "
                        + connector);
            }
            connector.setEndElement(element);
        }

    }

}
