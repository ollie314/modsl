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

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Collection;
import java.util.List;

import org.modsl.core.model.XY;
import org.modsl.core.model.graph.Edge;
import org.modsl.core.model.graph.Graph;
import org.modsl.core.model.graph.Vertex;

/**
 * Quick and dirty SVG renderer for a graph
 * 
 * @author avishnyakov
 */
public class QuickSvg {

	private Graph g;

	public QuickSvg(Graph g) {
		this.g = g;
	}

	/**
	 * Write SVG output to file
	 * 
	 * @param name
	 *            file name
	 * @throws IOException
	 */
	public void toFile(String name) throws IOException {
		PrintStream p = new PrintStream(new FileOutputStream(name));
		p.print(toSvg());
		p.close();
	}

	/**
	 * @return SVG output of the graph as String
	 */
	public String toSvg() {
		StringBuilder sb = new StringBuilder();
		header(sb);
		for (Vertex v : (Collection<Vertex>) g.getVertexes()) {
			toSvg(sb, v.getPosHistory());
		}
		for (Edge e : (Collection<Edge>) g.getEdges()) {
			toSvg(sb, e);
		}
		for (Vertex v : (Collection<Vertex>) g.getVertexes()) {
			toSvg(sb, v);
		}
		footer(sb);
		return sb.toString();
	}

	private void toSvg(StringBuilder sb, List<XY> posHistory) {
		int step = 1;
		sb.append("\t<path d=\"");
		for (XY p : posHistory) {
			if (step == 1) {
				sb.append("M");
				step = 2;
			} else if (step == 2) {
				sb.append("L");
				step = 3;
			}
			sb.append((int) p.x).append(",").append((int) p.y).append(" ");
		}
		sb.append("\" fill=\"none\" stroke=\"green\" stroke-width=\"1\"/>\n");
	}

	private void footer(StringBuilder sb) {
		sb.append("</svg>");
	}

	private void header(StringBuilder sb) {
		sb.append("<?xml version=\"1.0\"?>\n");
		sb.append("<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n\n");
		Tmpl.subs(sb, "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"${w}\" height=\"${h}\">\n", new String[] { "w",
				Tmpl.d2is(g.getSize().x + 21d), "h", Tmpl.d2is(g.getSize().y + 21d) });
	}

	private void toSvg(StringBuilder sb, Vertex v) {
		Tmpl.subs(sb, "\t<rect x=\"${x}\" y=\"${y}\" width=\"${w}\" height=\"${h}\" rx=\"5\" "
				+ "style=\"fill:#ccccff; stroke:#000000; stroke-width:1px;\"/>\n", new String[] { "x", Tmpl.d2is(v.getPosition().x),
				"y", Tmpl.d2is(v.getPosition().y), "w", Tmpl.d2is(v.getSize().x), "h", Tmpl.d2is(v.getSize().y) });
	}

	private void toSvg(StringBuilder sb, Edge e) {
		Tmpl.subs(sb, "\t<line x1=\"${x1}\" y1=\"${y1}\" x2=\"${x2}\" y2=\"${y2}\" "
				+ "style=\"stroke:#555555; stroke-width:1px;\"/>\n", new String[] { "x1",
				Tmpl.d2is(e.getStartVertex().getCenterPosition().x), "y1", Tmpl.d2is(e.getStartVertex().getCenterPosition().y), "x2",
				Tmpl.d2is(e.getEndVertex().getCenterPosition().x), "y2", Tmpl.d2is(e.getEndVertex().getCenterPosition().y) });
	}
}
