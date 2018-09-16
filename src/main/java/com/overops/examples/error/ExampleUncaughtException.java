package com.overops.examples.error;

public class ExampleUncaughtException extends RuntimeException {

    public ExampleUncaughtException() {
    }

    public ExampleUncaughtException(String message) {
        super(message);
    }

    public ExampleUncaughtException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExampleUncaughtException(Throwable cause) {
        super(cause);
    }

    public ExampleUncaughtException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
