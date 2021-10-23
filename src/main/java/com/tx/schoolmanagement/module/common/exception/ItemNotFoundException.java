package com.tx.schoolmanagement.module.common.exception;

/**
 * Exception to describe when an item not found in the system.
 */
public class ItemNotFoundException extends RuntimeException {

    /**
     * Creates a new instance of ItemNotFoundException.
     *
     * @param message Exception message.
     */
    public ItemNotFoundException(String message) {
        super(message);
    }

    /**
     * Creates a new instance of ItemNotFoundException.
     *
     * @param message Exception message.
     * @param cause   Throwable cause
     */
    public ItemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
