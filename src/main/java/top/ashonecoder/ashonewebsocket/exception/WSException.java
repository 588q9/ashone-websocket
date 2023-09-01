package top.ashonecoder.ashonewebsocket.exception;

/**
 * @author ashone
 * <p>
 * desc
 */
public class WSException extends RuntimeException {
    public WSException() {
    }

    public WSException(Throwable cause) {
        super(cause);
    }

    public WSException(String message) {
        super(message);
    }

    public static WSException exception(String message) {

        return new WSException(message);
    }

    public static WSException exception(Exception e) {

        return new WSException(e);
    }
}
