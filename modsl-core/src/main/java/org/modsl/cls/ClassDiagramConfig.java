package org.modsl.cls;

import org.modsl.core.config.AbstractConfig;

public class ClassDiagramConfig extends AbstractConfig<ClassDiagramTemplateProps, ClassDiagramLayoutProps> {

    public ClassDiagramConfig(String path, String name) {
        super(path, name, new ClassDiagramTemplateProps(), new ClassDiagramLayoutProps());
    }

}
