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
import org.modsl.core.config.FontTransform;

/**
 * Class diagram model configuration
 * 
 * @author avishnyakov
 *
 */
public class ClassDiagramConfig extends AbstractConfig<FontTransform> {

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

    public final FontTransform diagramHeaderFT = new FontTransform(diagramFontFamily, getIntegerProp("diagramHeaderFontSize"));
    public final FontTransform diagramFooterFT = new FontTransform(diagramFontFamily, getIntegerProp("diagramFooterFontSize"));
    public final FontTransform elementHeaderFT = new FontTransform(diagramFontFamily, getIntegerProp("elementHeaderFontSize"));
    public final FontTransform elementDetailFT = new FontTransform(diagramFontFamily, getIntegerProp("elementDetailFontSize"));
    public final FontTransform connectorFT = new FontTransform(diagramFontFamily, getIntegerProp("connectorFontSize"));

    public ClassDiagramConfig() {
        super("/config/cls/");
    }

}
