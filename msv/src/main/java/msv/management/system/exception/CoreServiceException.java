package msv.management.system.exception;

/**
 * @author rpsperera on 12/4/20
 */

public class CoreServiceException extends Exception {

    public CoreServiceException(String message) {
        super(message);
    }

    public CoreServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
