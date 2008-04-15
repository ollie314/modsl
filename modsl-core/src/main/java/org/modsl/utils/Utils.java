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

package org.modsl.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Miscellaneous utils.
 * @author avishnyakov
 */
public class Utils {

	/**
	 * Checks if a given object is an instance of the given class or is an instance of it's subclass
	 * @param object
	 * @param cls
	 * @return true if an object can be cast to the given class
	 */
	public static boolean isA(Object object, Class<?> cls) {
		return cls.isAssignableFrom(object.getClass());
	}

	/**
	 * Converts array of values into a map. Even element being a key to the 
	 * subsequent odd element. { "a", "b", "c", "d" } will be converted to ["a":"b", "c":"d"]
	 * @param arr array to covert
	 * @return map
	 */
	public static Map<String, Object> toMap(Object[] arr) {
		Map<String, Object> res = new HashMap<String, Object>();
		for (int i = 0; i < arr.length; i += 2) {
			res.put((String) arr[i], arr[i + 1]);
		}
		return res;
	}

}
