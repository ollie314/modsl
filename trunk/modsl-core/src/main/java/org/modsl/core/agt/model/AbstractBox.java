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

package org.modsl.core.agt.model;

import static java.lang.Math.abs;
import static java.lang.Math.signum;

/**
 * Abstract rectangle graph element
 * @author avishnyakov
 */

public abstract class AbstractBox<P extends AbstractElement<?>> extends AbstractElement<P> {

    /**
     * Position
     */
    protected Pt pos = new Pt();

    /**
     * Alt position (needed for layouts)
     */
    protected Pt altPos = new Pt();

    /**
     * Size
     */
    protected Pt size = new Pt();

    /**
     * Create new
     */
    public AbstractBox() {
        //
    }

    /**
     * Create new
     * @param type
     */
    public AbstractBox(MetaType type) {
        super(type);
    }

    /**
     * Create new
     * @param type
     * @param name
     */
    public AbstractBox(MetaType type, String name) {
        super(type, name);
    }

    public Pt getAltPos() {
        return altPos;
    }

    /**
     * @return center position (will be different from pos if size > 0)
     */
    public Pt getCtrPos() {
        return pos.plus(size.div(2d));
    }

    /**
     * @return position (top left corner for objects w/ size > 0)
     */
    public Pt getPos() {
        return pos;
    }

    /**
     * @return size
     */
    public Pt getSize() {
        return size;
    }

    /**
     * @param b other box
     * @return true if connected directly (no intermediate bends or nodes)
     */
    public boolean isConnectedTo(AbstractBox<?> b) {
        return false;
    }

    public void setAltPos(Pt altPos) {
        this.altPos = altPos;
    }

    /**
     * Set position (top left corner)
     * @param pos
     */
    public void setPos(Pt point) {
        this.pos = point;
    }

    /**
     * Set size
     * @param size
     */
    public void setSize(Pt size) {
        this.size = size;
    }

    /**
     * @return distance between centers of this box and the other box
     */
    public Pt getCtrDelta(AbstractBox<P> n2) {
        return n2.getCtrPos().minus(getCtrPos());
    }

    /**
     * @return cos(angle of the line between this box and the other box)
     */
    public double cos(AbstractBox<P> n2) {
        Pt delta = getCtrDelta(n2);
        return delta.x / delta.len();
    }

    /**
     * @return sin(angle of the line between this box and the other box)
     */
    public double sin(AbstractBox<P> n2) {
        Pt delta = getCtrDelta(n2);
        return delta.y / delta.len();
    }

    /**
     * @return tan(angle of the line between this box and the other box)
     */
    public double tan(AbstractBox<P> n2) {
        Pt delta = getCtrDelta(n2);
        return delta.y / delta.x;
    }

    public Pt[] getMinDeltaPts(AbstractBox<P> n2) {
        Pt[] res = new Pt[2];
        res[0] = this.getPort(this.sin(n2), this.cos(n2), this.tan(n2));
        res[1] = n2.getPort(n2.sin(this), n2.cos(this), n2.tan(this));
        return res;
    }

    public Pt getMinDelta(AbstractBox<P> n2) {
        Pt[] res = getMinDeltaPts(n2);
        return res[1].decBy(res[0]);
    }
    
    public Pt getPort(double sin, double cos, double tan) {
        Pt ap = new Pt();
        Pt cp = getCtrPos();
        Pt s = getSize();
        if (abs(tan()) > abs(tan)) {
            // i.e. line is crossing e2's side
            ap.x = cp.x + s.x * signum(cos) / 2d;
            ap.y = cp.y + s.x * tan * signum(cos) / 2d;
        } else {
            ap.x = cp.x + s.y / tan * signum(sin) / 2d;
            ap.y = cp.y + s.y * signum(sin) / 2d;
        }
        return ap;
    }
    
    /**
     * @return cos of angle between 0 and diagonal of this box
     */
    public double cos() {
        return size.x / size.len();
    }
    /**
     * @return sin of angle between 0 and diagonal of this box
     */
    public double sin() {
        return size.y / size.len();
    }

    /**
     * @return tan of angle between 0 and diagonal of this box
     */
    public double tan() {
        return size.y / size.x;
    }

}
