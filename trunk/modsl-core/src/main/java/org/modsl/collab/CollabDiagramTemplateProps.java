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

package org.modsl.collab;

import org.modsl.core.config.AbstractTemplateProps;
import org.modsl.core.config.FontTransform;

/**
 * Collaboration diagram template configuration
 * 
 * @author avishnyakov
 *
 */
public class CollabDiagramTemplateProps extends AbstractTemplateProps {

    public final int diagramDefaultWidth = getIntegerProp("diagramDefaultWidth");
    public final int diagramDefaultHeight = getIntegerProp("diagramDefaultHeight");

    public final boolean renderHistory = getBooleanProp("renderHistory");
    public final int diagramPadding = getIntegerProp("diagramPadding");

    public final String elementRx = getProp("elementRx");
    public final String diagramFontFamily = getProp("diagramFontFamily");

    public final FontTransform diagramHeaderFT = new FontTransform(diagramFontFamily, getIntegerProp("diagramHeaderFontSize"));
    public final FontTransform diagramFooterFT = new FontTransform(diagramFontFamily, getIntegerProp("diagramFooterFontSize"));
    public final FontTransform elementHeaderFT = new FontTransform(diagramFontFamily, getIntegerProp("elementHeaderFontSize"));
    public final FontTransform connectorFT = new FontTransform(diagramFontFamily, getIntegerProp("connectorFontSize"));

}