/**
 * Copyright 2008 Andrew Vishnyakov <avishn@gmail.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.modsl.core.agt.layout;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.modsl.core.agt.model.AbstractAGTModelTest;
import org.modsl.core.agt.model.Node;

public class SugiyamaLayoutTest extends AbstractAGTModelTest {

    protected SugiyamaLayout layout = new SugiyamaLayout();

    @Test
    public void degreeSorting() {
        List<Node<?>> sorted = layout.sortByOutDegree(root);
        assertEquals(sorted.size(), root.getNodes().size());
        assertTrue(sorted.get(0).getName().startsWith("Top"));
        assertTrue(sorted.get(sorted.size() - 1).getName().startsWith("Middle"));
    }
    
}
