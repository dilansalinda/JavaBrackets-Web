package msv.management.system.exception;

/**
 * @author rpsperera on 12/4/20
 */

public class RolePermissionServiceException extends CoreServiceException {

    public RolePermissionServiceException(String message) {
        super(message);
    }

    public RolePermissionServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
