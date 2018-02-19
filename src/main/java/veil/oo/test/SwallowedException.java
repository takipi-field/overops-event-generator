package veil.oo.test;

public class SwallowedException extends RuntimeException {

    public SwallowedException() {
        super();
    }

    public SwallowedException(String message) {
        super(message);
    }

    public SwallowedException(String message, Throwable cause) {
        super(message, cause);
    }

    public SwallowedException(Throwable cause) {
        super(cause);
    }

    protected SwallowedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
