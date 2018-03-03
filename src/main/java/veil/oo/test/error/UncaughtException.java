package veil.oo.test.error;

public class UncaughtException extends RuntimeException {

    public UncaughtException() {
    }

    public UncaughtException(String message) {
        super(message);
    }

    public UncaughtException(String message, Throwable cause) {
        super(message, cause);
    }

    public UncaughtException(Throwable cause) {
        super(cause);
    }

    public UncaughtException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
