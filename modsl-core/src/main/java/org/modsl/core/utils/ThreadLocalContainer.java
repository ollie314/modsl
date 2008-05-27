package org.modsl.core.utils;

import java.util.LinkedList;
import java.util.List;

public class ThreadLocalContainer {

    private static ThreadLocal<ThreadLocalContainer> threadLocal = new ThreadLocal<ThreadLocalContainer>();

    List<String> logMessages = new LinkedList<String>();

    public static void set(ThreadLocalContainer tlc) {
        threadLocal.set(tlc);
    }

    public static ThreadLocalContainer get() {
        ThreadLocalContainer tlc = (ThreadLocalContainer) threadLocal.get();
        if (tlc == null) {
            tlc = new ThreadLocalContainer();
            set(tlc);
        }
        return tlc;
    }

    public void addLogMessage(String msg) {
        logMessages.add(msg);
    }

    public List<String> extractLogMessages() {
        List<String> temp = logMessages;
        logMessages = new LinkedList<String>();
        return temp;
    }

}
