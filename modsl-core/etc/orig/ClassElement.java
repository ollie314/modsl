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

package OBSOLETE.org.modsl.cls.model;

import static java.lang.Math.exp;
import static java.lang.Math.max;

import java.util.ArrayList;
import java.util.List;

import org.modsl.core.agt.model.FontTransform;

import OBSOLETE.org.modsl.core.model.XY;
import OBSOLETE.org.modsl.core.model.diagram.Element;

/**
 * Class/interface element implementation
 * 
 * @author avishnyakov
 */
public class ClassElement extends Element<ClassDiagram, ClassElementDetail> {

    protected List<ClassElementDetail> attributes = new ArrayList<ClassElementDetail>();
    protected List<ClassElementDetail> methods = new ArrayList<ClassElementDetail>();

    public ClassElement(String name, ClassDiagram parent) {
        super(parent, name);
    }

    protected int getExtendsFromCount() {
        int ef = 0;
        for (ClassConnector c : parent.getConnectors()) {
            if (c.getStartElement().equals(this)
                    && (ClassConnector.Type.IMPLEMENTS.equals(c.getType()) || ClassConnector.Type.EXTENDS.equals(c.getType()))) {
                ef++;
            }
        }
        return ef;
    }

    public void calcWeight() {
        setWeight(1d / (1d + exp(-getExtendsFromCount())));
    }
    public List<ClassElementDetail> getAttributes() {
        return attributes;
    }

    public List<ClassElementDetail> getMethods() {
        return methods;
    }

    /**
     * Calculate size given font transform
     * @param elementHeaderFT
     * @param elementDetailFT
     */
    public void calcSize(FontTransform elementHeaderFT, FontTransform elementDetailFT) {

        double maxExtStringWidth = elementHeaderFT.getExtStringWidth(name);
        double maxExtHeight = elementHeaderFT.getExtHeight(1);

        XY attrAreaPosition = new XY(0, maxExtHeight);
        for (int i = 0; i < attributes.size(); i++) {
            ClassElementDetail ed = attributes.get(i);
            ed.calcSizeAndPosition(attrAreaPosition, elementDetailFT, i);
            maxExtStringWidth = max(maxExtStringWidth, ed.getSize().x);
            maxExtHeight = ed.getRelativePosition().y + ed.getSize().y;
        }

        XY methodAreaPosition;
        if (attributes.size() > 0) {
            maxExtHeight += elementDetailFT.getBottomPadding();
            methodAreaPosition = new XY(0, maxExtHeight);
        } else {
            methodAreaPosition = attrAreaPosition;
        }

        for (int i = 0; i < methods.size(); i++) {
            ClassElementDetail ed = methods.get(i);
            methods.get(i).calcSizeAndPosition(methodAreaPosition, elementDetailFT, i);
            maxExtStringWidth = max(maxExtStringWidth, ed.getSize().x);
            maxExtHeight = ed.getRelativePosition().y + ed.getSize().y;
        }

        if (methods.size() > 0) {
            maxExtHeight += elementDetailFT.getBottomPadding();
        }

        if (methods.size() == 0 && attributes.size() == 0) {
            maxExtHeight += elementDetailFT.getSize();
        }

        size.x = maxExtStringWidth;
        size.y = maxExtHeight;

    }

}
