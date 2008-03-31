package org.modsl.primitives;

import java.util.HashMap;
import java.util.Map;

public class ClassPrimitive {
	
	protected Map<String, String> im = new HashMap<String, String>();
	protected Map<String, String> sm = new HashMap<String, String>();
	
	public ClassPrimitive m(String name, String type) {
		im.put(name, type);
		return this;
	}

	public ClassPrimitive s(String name, String type) {
		sm.put(name, type);
		return this;
	}

}
