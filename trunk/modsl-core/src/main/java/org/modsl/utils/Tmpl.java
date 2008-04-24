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

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Template and pattern matching utility methods
 * 
 * @author avishnyakov
 */
public class Tmpl {

	protected static void subs(StringBuilder buffer, String original, Map<String, Object> substitutions) {
		Pattern p = Pattern.compile("\\$\\{(\\w+)\\}");
		Matcher m = p.matcher(original);
		int s = 0;
		while (m.find()) {
			String lookup = (String) substitutions.get(m.group(1));
			buffer.append(original, s, m.start());
			buffer.append(lookup);
			s = m.end();
		}
		buffer.append(original, s, original.length());
	}

	/**
	 * Substitute all occurrences in original with substitutions provided in
	 * String array and store the result in buffer
	 * 
	 * @param buffer
	 * @param original
	 * @param substitutions
	 */
	public static void subs(StringBuilder buffer, String original, String[] substitutions) {
		subs(buffer, original, Utils.toMap(substitutions));
	}

	/**
	 * Convert to int and then to String (for SVG output)
	 * 
	 * @param d
	 * @return
	 */
	public static String d2is(double d) {
		return Integer.toString((int) d);
	}

	/**
	 * Convert from String to double
	 * 
	 * @param obj
	 * @return
	 */
	public static double o2d(Object obj) {
		return Double.parseDouble(obj.toString());
	}

}
