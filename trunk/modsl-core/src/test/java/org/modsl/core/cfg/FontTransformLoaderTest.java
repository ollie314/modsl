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

package org.modsl.core.cfg;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.modsl.core.agt.TMetaType;
import org.modsl.core.render.StyleLoader;

public class FontTransformLoaderTest {

    protected Logger log = Logger.getLogger(getClass());

    @Test
    public void load() {
        FontTransformLoader ftl = new FontTransformLoader("test/props/ft", "demo", TMetaType.class);
        ftl.load();
    }

}
