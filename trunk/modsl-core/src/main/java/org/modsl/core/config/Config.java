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

package org.modsl.core.config;

import org.apache.log4j.Logger;

/**
 * Diagram config set
 * 
 * @author avishnyakov
 *
 * @param <F> font size transformer class
 */
public class Config<T extends AbstractTemplateProps, L extends AbstractLayoutProps> {

    protected final Logger log = Logger.getLogger(getClass());

    protected String path;
    protected String name;

    protected T templateProps;
    protected L layoutProps;

    /**
     * New configuration at given path for specific diagram name
     * @param path
     * @param name
     */
    public Config(String path, String name, T templateProps, L layoutProps) {
        this.path = path;
        this.name = name;
        this.templateProps = templateProps;
        this.templateProps.load(path, name, "template.properties");
        this.layoutProps = layoutProps;
        this.layoutProps.load(path, name, "layout.properties");
    }

    public T getTemplateProps() {
        return templateProps;
    }

    public L getLayoutProps() {
        return layoutProps;
    }

}
