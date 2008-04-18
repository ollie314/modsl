package org.modsl.collab;

import org.modsl.core.config.AbstractConfig;

public class CollabDiagramConfig extends AbstractConfig<CollabDiagramTemplateProps, CollabDiagramLayoutProps> {

    public CollabDiagramConfig(String path, String name) {
        super(path, name, new CollabDiagramTemplateProps(), new CollabDiagramLayoutProps());
    }

}
