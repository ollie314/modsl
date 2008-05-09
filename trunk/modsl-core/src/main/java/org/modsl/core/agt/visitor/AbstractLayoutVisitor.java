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

package org.modsl.core.agt.visitor;

import java.util.Map;

import org.apache.log4j.Logger;
import org.modsl.core.agt.model.MetaType;

public abstract class AbstractLayoutVisitor extends AbstractVisitor {

    protected Logger log = Logger.getLogger(getClass());

    protected MetaType type;

    public AbstractLayoutVisitor(MetaType type) {
        this.type = type;
    }

    public abstract String getConfigName();

    public abstract void setLayoutConfig(Map<String, String> propMap);

}
