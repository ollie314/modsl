package org.modsl.core.lang;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.modsl.core.Utils;
import org.modsl.core.agt.model.Pt;

public class SVGCollector {

	protected List<String> urls = new LinkedList<String>();

	private String name;
	private String path;

	public SVGCollector(String path, String name) {
		this.path = path;
		this.name = name;
	}

	public void collect(String root, String svg, Pt reqSize) throws IOException {
		String n = name + "_" + root + ".svg";
		Utils.toFile(path + "/" + n, svg);
		urls.add(n);
		Utils.toFile(path + "/" + name + ".html", html(reqSize));
	}

	private String html(Pt size) {
		StringBuilder sb = new StringBuilder("<html>\n\t<header>\n\t\t<title>");
		sb.append(name).append("</title>\n\t<header>\n\t<body>");
		for (String url : urls) {
			sb.append("\n\t<embed src=\"").append(url).append("\" width=\"").append(size.x).append("\" height=\"").append(
					size.y).append("\"/>");
		}
		sb.append("\n\t</body>\n</html>");
		return sb.toString();
	}

	public String getName() {
		return name;
	}

	public String getPath() {
		return path;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
