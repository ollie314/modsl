package org.modsl.core.builder.common;

public class AbstractModslException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AbstractModslException() {
        super();
    }

    public AbstractModslException(String message) {
        super(message);
    }

    public AbstractModslException(Throwable cause) {
        super(cause);
    }

    public AbstractModslException(String message, Throwable cause) {
        super(message, cause);
    }

}
