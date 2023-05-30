package msv.management.system.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CoreRuntimeException extends RuntimeException {


    public CoreRuntimeException(String message) {
        super(message);
        log.info("Exception message {}", message);
    }

    public CoreRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public CoreRuntimeException(Throwable cause) {
        super(cause);
    }

    public CoreRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

