package com.overops.examples.error;

public class ExampleSwallowedException extends RuntimeException {

    public ExampleSwallowedException() {
        super();
    }

    public ExampleSwallowedException(String message) {
        super(message);
    }

    public ExampleSwallowedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExampleSwallowedException(Throwable cause) {
        super(cause);
    }

    protected ExampleSwallowedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
