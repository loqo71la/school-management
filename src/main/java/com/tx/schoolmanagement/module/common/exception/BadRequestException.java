package com.tx.schoolmanagement.module.common.exception;

import static com.tx.schoolmanagement.module.common.constant.ResponseConstants.BAD_REQUEST;

/**
 * Exception to describe a malformed request.
 */
public class BadRequestException extends RuntimeException {

    /**
     * Creates a new instance of BadRequestException.
     */
    public BadRequestException() {
        super(BAD_REQUEST);
    }
}
