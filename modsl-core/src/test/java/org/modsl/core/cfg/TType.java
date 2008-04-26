/**
 * 
 */
package org.modsl.core.cfg;

import org.modsl.core.agt.model.MetaType;
import org.modsl.core.agt.model.MetaTypeConfig;

public enum TType implements MetaType {

	GRAPH, NODE, EDGE;

	protected MetaTypeConfig config = new MetaTypeConfig();

	@Override
	public MetaTypeConfig getConfig() {
		return config;
	}

}