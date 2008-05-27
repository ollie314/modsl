package org.modsl.core.utils;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

public class ThreadLocalAppender extends AppenderSkeleton {

    protected void append(LoggingEvent event) {
        ThreadLocalContainer.get().logMessages.add(event.getRenderedMessage());
    }

    public boolean requiresLayout() {
        return true;
    }

    public void close() {
        ThreadLocalContainer.get().logMessages.clear();
    }

}
