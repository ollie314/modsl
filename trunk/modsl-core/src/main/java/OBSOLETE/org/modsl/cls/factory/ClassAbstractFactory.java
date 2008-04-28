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

package OBSOLETE.org.modsl.cls.factory;

import java.util.ArrayList;
import java.util.List;

import OBSOLETE.org.modsl.core.builder.AbstractFactory;
import OBSOLETE.org.modsl.core.builder.BuilderException;

/**
 * Abstract factory for all class diagram objects
 * 
 * @author avishnyakov
 */
public abstract class ClassAbstractFactory extends AbstractFactory {

    protected static List<AbstractFactory> factories = new ArrayList<AbstractFactory>();

    static {
        factories.add(new ClassDiagramFactory());
        factories.add(new ClassElementFactory());
        factories.add(new ClassElementDetailFactory());
        factories.add(new ClassConnectorFactory());
    }

    public static AbstractFactory findFactory(String metaKey) {
        for (AbstractFactory af : factories) {
            if (af.getMetaKeys().contains(metaKey)) {
                return af;
            }
        }
        throw new BuilderException("Cannot find factory to build " + metaKey);
    }

}