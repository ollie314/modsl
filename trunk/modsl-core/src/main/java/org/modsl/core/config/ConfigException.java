package org.modsl.core.config;

import org.modsl.core.builder.common.AbstractModslException;

public class ConfigException extends AbstractModslException {

    private static final long serialVersionUID = 1L;

    public ConfigException() {
        super();
    }

    public ConfigException(String message) {
        super(message);
    }

    public ConfigException(Throwable cause) {
        super(cause);
    }

    public ConfigException(String message, Throwable cause) {
        super(message, cause);
    }

}
