package com.tx.schoolmanagement.module.common.exception;

/**
 * Exception to describe a malformed request.
 */
public class BadRequestException extends RuntimeException {

    /**
     * Creates a new instance of BadRequestException.
     *
     * @param message Exception message.
     */
    public BadRequestException(String message) {
        super(message);
    }

    /**
     * Creates a new instance of BadRequestException.
     *
     * @param message Exception message.
     * @param cause   Throwable cause
     */
    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
