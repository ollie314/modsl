package org.modsl.core.lang.basic;

import org.modsl.core.agt.model.MetaType;
import org.modsl.core.cfg.LayoutGroupLoader;


public class BasicLayoutGroupLoader extends LayoutGroupLoader {

	public BasicLayoutGroupLoader(String path, Class<? extends MetaType> metaTypeClass) {
		super(path, metaTypeClass);
	}

}
