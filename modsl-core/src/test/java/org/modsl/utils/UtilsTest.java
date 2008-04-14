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

package org.modsl.utils;

import java.awt.FontMetrics;

import org.modsl.core.config.FontSizeTransform;

import junit.framework.TestCase;

public class UtilsTest extends TestCase {

    public void testGetFontMetrics() {
        FontMetrics fm = new FontSizeTransform("Serif", 12).getFontMetrics();
        assertTrue(fm.stringWidth("test") > 0d);
        //assertEquals(fm.getHeight(), 12);
    }

}
