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

import org.modsl.core.config.AbstractConfig;

/**
 * Class diagram model configuration
 * 
 * @author avishnyakov
 *
 */
public class ClassDiagramConfig extends AbstractConfig<ClassFontSizeTransform> {

	public final int initLayoutMaxRounds = getIntegerProp("initLayoutMaxRounds");

	public final int layoutMaxIterations = getIntegerProp("layoutMaxIterations");
	public final double layoutTempMultiplier = getDoubleProp("layoutTempMultiplier");
	public final double layoutAttractionMultiplier = getDoubleProp("layoutAttractionMultiplier");
	public final double layoutRepulsionMultiplier = getDoubleProp("layoutRepulsionMultiplier");

	public final boolean renderHistory = getBooleanProp("renderHistory");

	public final int diagramPadding = getIntegerProp("diagramPadding");

	public final int diagramDefaultWidth = getIntegerProp("diagramDefaultWidth");
	public final int diagramDefaultHeight = getIntegerProp("diagramDefaultHeight");

	public final String elementRx = getProp("elementRx");

	public final String diagramFontFamily = getProp("diagramFontFamily");

	public final ClassFontSizeTransform diagramHeaderFST = new ClassFontSizeTransform(diagramFontFamily,
			getIntegerProp("diagramHeaderFontSize"));
	public final ClassFontSizeTransform diagramFooterFST = new ClassFontSizeTransform(diagramFontFamily,
			getIntegerProp("diagramFooterFontSize"));
	public final ClassFontSizeTransform elementHeaderFST = new ClassFontSizeTransform(diagramFontFamily,
			getIntegerProp("elementHeaderFontSize"));
	public final ClassFontSizeTransform elementDetailFST = new ClassFontSizeTransform(diagramFontFamily,
			getIntegerProp("elementDetailFontSize"));
	public final ClassFontSizeTransform connectorFST = new ClassFontSizeTransform(diagramFontFamily,
			getIntegerProp("connectorFontSize"));

	public ClassDiagramConfig() {
		super("/config/cls/");
	}

}
