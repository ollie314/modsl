package org.modsl.core;

public abstract class ModslCore {

    protected abstract void preAdjust();
    protected abstract void layout();
    protected abstract void render();
    
    final public void process() {
        preAdjust();
        layout();
        render();
    }
    
}
