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

package org.modsl.collab.factory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.modsl.collab.model.CollabConnector;
import org.modsl.collab.model.CollabElement;
import org.modsl.core.builder.BuilderException;

/**
 * Creates collaboration diagram connectors from the given Groovy script input
 * 
 * @author avishnyakov
 *
 */
public class CollabConnectorFactory extends CollabAbstractFactory {

    protected Map<String, Object> typeMap;

    protected int sequence = 1;

    public CollabConnectorFactory() {
        metaKeys = Arrays.asList(new String[] { "_call" });
    }

    public CollabConnector build(String metaKey, String value, Object current, Map<String, Object> map) {
        checkParentClass(metaKey, current, CollabElement.class);
        CollabElement se = (CollabElement) current;
        String msg = getNullableStringAttribute(null, "_message", map);
        CollabConnector c = new CollabConnector(se.getParent(), sequence++ + (msg == null ? "" : (":" + msg)));
        c.setStartElement(se);
        c.setEndElementName(getMandatoryStringAttribute("_to", map));
        return c;
    }

    public List<String> getMetaKeys() {
        return metaKeys;
    }

    public static void finalizeConnector(CollabConnector connector) {
        if (connector.getEndElement() == null) {
            if (connector.getEndElementName() == null) {
                throw new BuilderException("Connector end element is undefined " + connector);
            }
            CollabElement element = connector.getParent().getElement(connector.getEndElementName());
            if (element == null) {
                throw new BuilderException("Cannot find end element '" + connector.getEndElementName() + "' for connector "
                        + connector);
            }
            connector.setEndElement(element);
        }

    }

}
