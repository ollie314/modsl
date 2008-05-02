package org.modsl.core.agt.common;

import java.util.HashMap;
import java.util.Map;

import org.modsl.core.agt.model.MetaType;
import org.modsl.core.agt.model.Node;

public class MetaTypeMapDecorator extends AbstractDecorator<Node<?>> {

	protected Map<String, Object> metaTypeMap;

	public MetaTypeMapDecorator(Class<? extends MetaType> mtc) {
		Object[] mta = mtc.getEnumConstants();
		metaTypeMap = new HashMap<String, Object>(mta.length);
		for (Object mt : mta) {
			metaTypeMap.put(mt.toString(), mt);
		}
	}

	public Map<String, Object> getMeta() {
		return metaTypeMap;
	}

}
