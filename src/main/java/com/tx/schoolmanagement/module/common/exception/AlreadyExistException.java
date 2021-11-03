package com.tx.schoolmanagement.module.common.exception;

/**
 * Exception to describe when a operation is unauthorized.
 */
public class AlreadyExistException  extends Exception{

    /**
     * Creates a new instance of UnauthorizedException.
     *
     * @param message Exception message.
     */
    public AlreadyExistException(String message) {
        super(message);
    }
}
