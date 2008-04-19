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

package org.modsl.collab.layout;

import org.modsl.collab.CollabDiagramLayoutProps;
import org.modsl.collab.model.CollabDiagram;
import org.modsl.core.layout.AbstractLayout;

/**
 * Layout flow for the collaboration diagram. Aggregates other layout calls.
 * 
 * @author avishnyakov
 *
 */
public class CollabDiagramLayout extends AbstractLayout<CollabDiagram, CollabDiagramLayoutProps> {

    public CollabDiagramLayout(CollabDiagramLayoutProps props) {
        super(props);
    }

    public void apply(CollabDiagram diagram) {
        new CollabInitialCirclePosition(props).apply(diagram);
        new CollabFRLayout(props).apply(diagram);
        diagram.timestamp("layout");
    }

}
