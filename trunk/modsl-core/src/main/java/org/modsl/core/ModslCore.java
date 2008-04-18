package org.modsl.core;

public abstract class ModslCore {

    protected abstract void metrics();
    protected abstract void layout();
    protected abstract void render();
    
    final public void process() {
        metrics();
        layout();
        render();
    }
    
}
