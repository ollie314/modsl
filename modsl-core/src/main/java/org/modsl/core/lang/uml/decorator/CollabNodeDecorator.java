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

package org.modsl.core.lang.uml.decorator;

import org.modsl.core.agt.decor.SimpleNodeDecorator;
import org.modsl.core.agt.model.Pt;

public class CollabNodeDecorator extends SimpleNodeDecorator {

	public Pt getUnderline1() {
		return new Pt(getTextPos().x, parent.getPos().y + getFt().getExtBaseline(0) + 2);
	}

	public Pt getUnderline2() {
		return new Pt(getTextPos().x + getFt().getStringWidth(parent.getName()), parent.getPos().y + getFt().getExtBaseline(0)
				+ 2);
	}

}
