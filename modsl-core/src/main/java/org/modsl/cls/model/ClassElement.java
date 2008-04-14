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

package org.modsl.cls.model;

import static java.lang.Math.exp;

import java.util.ArrayList;
import java.util.List;

import org.modsl.core.model.diagram.Element;

/**
 * Class/interface element implementation
 * 
 * @author avishnyakov
 */
public class ClassElement extends Element<ClassDiagram, ClassElementDetail> {

	protected List<ClassElementDetail> attributes = new ArrayList<ClassElementDetail>();
	protected List<ClassElementDetail> methods = new ArrayList<ClassElementDetail>();

	public ClassElement(String metaKey, String name, ClassDiagram parent) {
		super(metaKey, parent, name);
	}

	public int getExtendsFromCount() {
		int ef = 0;
		for (ClassConnector c : parent.getConnectors()) {
			if (c.getStartElement().equals(this) && ("_extends".equals(c.getMetaKey()) || "_implements".equals(c.getMetaKey()))) {
				ef++;
			}
		}
		return ef;
	}

	public void calculateWeight() {
		setWeight(1d / (1d + exp(-getExtendsFromCount())));
	}

	public String toString() {
		return name + ":" + weight;
	}

	public List<ClassElementDetail> getAttributes() {
		return attributes;
	}

	public List<ClassElementDetail> getMethods() {
		return methods;
	}

}
