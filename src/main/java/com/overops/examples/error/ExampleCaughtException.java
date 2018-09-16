package com.overops.examples.error;

public class ExampleCaughtException extends Exception {
    public ExampleCaughtException() {
    }

    public ExampleCaughtException(String message) {
        super(message);
    }

    public ExampleCaughtException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExampleCaughtException(Throwable cause) {
        super(cause);
    }

    public ExampleCaughtException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
