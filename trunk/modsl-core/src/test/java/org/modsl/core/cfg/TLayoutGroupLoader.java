package org.modsl.core.cfg;

import org.modsl.core.agt.layout.Layout;

public class TLayoutGroupLoader extends LayoutGroupLoader {

	public TLayoutGroupLoader(String path, Class<TType> metaTypeClass) {
		super(path, metaTypeClass);
		TType.NODE.getConfig().setLayout(new Layout[] { new T1Layout(), new T2Layout() });
		TType.EDGE.getConfig().setLayout(new Layout[] { new T3Layout() });
	}

}
