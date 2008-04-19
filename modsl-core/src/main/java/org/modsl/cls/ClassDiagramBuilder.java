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

package org.modsl.cls;

import java.util.Map;

import org.apache.log4j.Logger;
import org.modsl.cls.factory.ClassAbstractFactory;
import org.modsl.cls.factory.ClassConnectorFactory;
import org.modsl.cls.model.ClassConnector;
import org.modsl.cls.model.ClassDiagram;
import org.modsl.core.builder.AbstractBuilder;
import org.modsl.utils.Utils;

/**
 * Builds a class diagram model from a Groovy script
 * 
 * @author avishnyakov
 *
 */
public class ClassDiagramBuilder extends AbstractBuilder {

	Logger log = Logger.getLogger(this.getClass());

	protected Object createObject(Object metaKey, Object current, Map attributes) {
		return ClassAbstractFactory.findFactory((String) metaKey).build((String) metaKey, (String) attributes.remove(VALUE),
				current, attributes);
	}

	protected void nodeCompleted(Object parent, Object node) {
		if ((node != null) && Utils.isA(node, ClassDiagram.class)) {
			finalizeConnectors((ClassDiagram) node);
		}
		super.nodeCompleted(parent, node);
	}

	protected void finalizeConnectors(ClassDiagram diagram) {
		for (ClassConnector connector : diagram.getConnectors()) {
			ClassConnectorFactory.finalizeConnector(connector);
		}
	}

}
