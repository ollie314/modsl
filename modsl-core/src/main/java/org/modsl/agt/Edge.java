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

package org.modsl.agt;

/**
 * Graph edge
 * 
 * @author avishnyakov
 * 
 */
public class Edge extends AbstractGraphElement {

    protected AbstractGraphElement node1;
    protected AbstractGraphElement node2;
    protected String node1Name;
    protected String node2Name;

    public Edge(String node1Name, String node2Name) {
        this.node1Name = node1Name;
        this.node2Name = node2Name;
    }

    public String toString() {
        return name + "(" + (node1 == null ? "*" + node1Name : node1.getName()) + "-"
                + (node2 == null ? "*" + node2Name : node2.getName()) + ")";
    }

}