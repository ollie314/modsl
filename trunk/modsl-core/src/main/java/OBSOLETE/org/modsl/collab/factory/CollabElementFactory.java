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

package OBSOLETE.org.modsl.collab.factory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import OBSOLETE.org.modsl.collab.model.CollabDiagram;
import OBSOLETE.org.modsl.collab.model.CollabElement;

/**
 * Creates collaboration diagram elements from the given Groovy script input
 * 
 * @author avishnyakov
 *
 */
public class CollabElementFactory extends CollabAbstractFactory {

    public CollabElementFactory() {
        metaKeys = Arrays.asList(new String[] { "_object" });
    }

    public CollabElement build(String metaKey, String value, Object current, Map<String, Object> map) {
        checkParentClass(metaKey, current, CollabDiagram.class);
        return new CollabElement(value, (CollabDiagram) current);
    }

    public List<String> getMetaKeys() {
        return metaKeys;
    }

}
