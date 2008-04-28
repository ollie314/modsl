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

package OBSOLETE.org.modsl.core.layout;


import OBSOLETE.org.modsl.core.config.AbstractTemplateProps;
import OBSOLETE.org.modsl.core.model.diagram.Diagram;

/**
 * Layout algorithm interface
 * 
 * @author avishnyakov
 */
public abstract class AbstractMetricsAdjustment<D extends Diagram<?, ?, ?>, P extends AbstractTemplateProps> {

    protected P props;

    public AbstractMetricsAdjustment(P props) {
        this.props = props;
    }

    /**
     * Run layout algorithm, update coordinates of the graph
     */
    public abstract void apply(D Diagram);

}
