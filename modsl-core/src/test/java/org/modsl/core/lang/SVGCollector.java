/**
 * Copyright 2008 Andrew Vishnyakov <avishn@gmail.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.modsl.core.lang;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.modsl.core.agt.model.Pt;
import org.modsl.core.utils.Utils;

public class SVGCollector {

    protected List<String> urls = new LinkedList<String>();
    protected List<Pt> sizes = new LinkedList<Pt>();

    private String name;
    private String path;

    public SVGCollector(String path, String name) {
        this.path = path;
        this.name = name;
    }

    public void collect(String graph, String svg, Pt reqSize) throws IOException {
        String n = name + "_" + graph + ".svg";
        Utils.toFile(path + "/" + n, svg);
        urls.add(n);
        sizes.add(reqSize);
        Utils.toFile(path + "/" + name + ".html", html());
    }

    private String html() {
        StringBuilder sb = new StringBuilder("<html>\n\t<header>\n\t\t<title>");
        sb.append(name).append("</title>\n\t<header>\n\t<body>");
        for (int i = 0; i < urls.size(); i++) {
            String url = urls.get(i);
            Pt size = sizes.get(i);
            sb.append("\n\t<embed src=\"").append(url).append("\" width=\"").append(size.x).append("\" height=\"").append(size.y)
                    .append("\"/>");
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
