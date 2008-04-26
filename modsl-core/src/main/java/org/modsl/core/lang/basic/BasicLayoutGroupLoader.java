package org.modsl.core.lang.basic;

import org.modsl.core.agt.layout.Layout;
import org.modsl.core.agt.layout.SimpleLabelLayout;
import org.modsl.core.cfg.LayoutGroupLoader;

public class BasicLayoutGroupLoader extends LayoutGroupLoader {

	public BasicLayoutGroupLoader(String path, Class<BasicType> metaTypeClass) {
		super(path, metaTypeClass);
		BasicType.NODE.getConfig().setLayout(new Layout[] { new SimpleLabelLayout() });
	}
}
