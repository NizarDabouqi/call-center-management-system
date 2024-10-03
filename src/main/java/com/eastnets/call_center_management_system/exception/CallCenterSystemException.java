package com.eastnets.call_center_management_system.exception;

public class CallCenterSystemException extends RuntimeException {

    public CallCenterSystemException(String message, Throwable exception) {
        super(message, exception);
    }

    public CallCenterSystemException(String message) {
        super(message);
    }
}
