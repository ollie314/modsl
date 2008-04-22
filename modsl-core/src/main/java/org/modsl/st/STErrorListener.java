/**
 * 
 */
package org.modsl.st;

import org.antlr.stringtemplate.StringTemplateErrorListener;
import org.apache.log4j.Logger;

public class STErrorListener implements StringTemplateErrorListener {

    protected Logger log = Logger.getLogger(getClass());

    @Override
    public void error(String m, Throwable t) {
        log.error(m, t);
    }

    @Override
    public void warning(String m) {
        log.warn(m);
    }

}