package org.modsl.core.cfg;

import org.modsl.core.agt.model.MetaType;

public class FontTransformLoader extends AbstractPropLoader {

    protected Class<? extends MetaType> metaTypeClass;

    public FontTransformLoader(String path, String name, Class<? extends MetaType> metaTypeClass) {
        super(path, name);
        this.metaTypeClass = metaTypeClass;
    }

    public void load() {
        String name = "serif";
        String size = "12";
        for (MetaType mt : metaTypeClass.getEnumConstants()) {
            String n = getProp(mt.toString() + ".name");
            if (n == null) {
                n = name;
            } else {
                name = n;
            }
            String s = getProp(mt.toString() + ".size");
            if (s == null) {
                s = size;
            } else {
                size = s;
            }
            FontTransform ft = new FontTransform(n, Integer.parseInt(s));
            mt.setFontTransform(ft);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(name);
        sb.append(" [");
        for (MetaType mt : metaTypeClass.getEnumConstants()) {
            sb.append(mt.toString()).append(":").append(mt.getFontTransform()).append(" ");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");
        return sb.toString();
    }
}
