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

package org.modsl.core.builder;

import groovy.lang.Closure;
import groovy.util.BuilderSupport;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Superclass for all Groovy based builders
 * 
 * @author avishnyakov
 *
 */
public abstract class AbstractBuilder extends BuilderSupport {

    public static final String VALUE = "value";
    Logger log = Logger.getLogger(this.getClass());

    public AbstractBuilder() {
        super();
    }

    public AbstractBuilder(BuilderSupport proxyBuilder) {
        super(proxyBuilder);
    }

    public AbstractBuilder(Closure nameMappingClosure, BuilderSupport proxyBuilder) {
        super(nameMappingClosure, proxyBuilder);
    }

    /**
     * Creates a model's object given the node data
     * @param name object name
     * @param current parent object
     * @param attributes map of Groovy builder node's attributes
     * @return new model object
     */
    protected abstract Object createObject(Object name, Object current, Map attributes);

    protected Object createNode(Object name) {
        try {
            return createObject(name, getCurrent(), new HashMap());
        } catch (BuilderException ex) {
            log.error("createNode(" + name + ")", ex);
            return null;
        }
    }

    @Override
    protected Object createNode(Object name, Object value) {
        try {
            Map attributes = new HashMap();
            attributes.put(VALUE, value);
            return createObject(name, getCurrent(), attributes);
        } catch (BuilderException ex) {
            log.error("createNode(" + name + ", " + value + ")", ex);
            return null;
        }
    }

    @Override
    protected Object createNode(Object name, Map attributes) {
        try {
            return createObject(name, getCurrent(), attributes);
        } catch (BuilderException ex) {
            log.error("createNode(" + name + ", " + attributes + ")", ex);
            return null;
        }
    }

    @Override
    protected Object createNode(Object name, Map attributes, Object value) {
        try {
            attributes.put(VALUE, value);
            return createObject(name, getCurrent(), attributes);
        } catch (BuilderException ex) {
            log.error("createNode(" + name + ", " + attributes + ", " + value + ")", ex);
            return null;
        }
    }

    @Override
    protected void setParent(Object parent, Object child) {
        // do nothing
    }

}