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

import org.modsl.core.builder.common.AbstractModslException;

/**
 * Builder/parser errors are supposed to be reported through this class or its subclasses
 * 
 * @author avishnyakov
 *
 */
public class BuilderException extends AbstractModslException {

    private static final long serialVersionUID = 1L;

    public BuilderException() {
        super();
    }

    public BuilderException(String message, Throwable cause) {
        super(message, cause);
    }

    public BuilderException(String message) {
        super(message);
    }

    public BuilderException(Throwable cause) {
        super(cause);
    }

}
