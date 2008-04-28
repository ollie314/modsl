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

package OBSOLETE.org.modsl.collab;

import java.util.Map;

import org.apache.log4j.Logger;
import org.modsl.core.Utils;

import OBSOLETE.org.modsl.collab.factory.CollabAbstractFactory;
import OBSOLETE.org.modsl.collab.factory.CollabConnectorFactory;
import OBSOLETE.org.modsl.collab.model.CollabConnector;
import OBSOLETE.org.modsl.collab.model.CollabDiagram;
import OBSOLETE.org.modsl.core.builder.AbstractBuilder;

/**
 * Builds a collaboration diagram model from a Groovy script
 * 
 * @author avishnyakov
 *
 */
public class CollabDiagramBuilder extends AbstractBuilder {

	Logger log = Logger.getLogger(this.getClass());

	protected Object createObject(Object metaKey, Object current, Map attributes) {
		return CollabAbstractFactory.findFactory((String) metaKey).build((String) metaKey, (String) attributes.remove(VALUE),
				current, attributes);
	}

	protected void nodeCompleted(Object parent, Object node) {
		if ((node != null) && Utils.isA(node, CollabDiagram.class)) {
			finalizeConnectors((CollabDiagram) node);
		}
		super.nodeCompleted(parent, node);
	}

	protected void finalizeConnectors(CollabDiagram diagram) {
		for (CollabConnector connector : diagram.getConnectors()) {
			CollabConnectorFactory.finalizeConnector(connector);
		}
	}

}
