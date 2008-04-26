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

package org.modsl.core.cfg;

import java.util.Map;

import org.modsl.core.agt.layout.Layout;
import org.modsl.core.agt.model.Edge;
import org.modsl.core.agt.model.Node;

public class T1Layout implements Layout {

    protected Map<String, String> propMap;

    @Override
    public String getConfigName() {
        return "test1_layout_config";
    }

    @Override
    public void setConfig(Map<String, String> propMap) {
        this.propMap = propMap;
    }

    @Override
    public void apply(Edge<?> edge) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void apply(Node<?> node) {
        // TODO Auto-generated method stub
        
    }

}
