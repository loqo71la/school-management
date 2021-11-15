package com.tx.schoolmanagement.module.common.exception;

import static com.tx.schoolmanagement.module.common.constant.ResponseConstants.NOT_FOUND;

/**
 * Exception to describe when an item not found in the system.
 */
public class NotFoundException extends RuntimeException {

    /**
     * Creates a new instance of NotFoundException.
     *
     * @param model model name.
     * @param id    model id.
     */
    public NotFoundException(String model, String id) {
        super(String.format(NOT_FOUND, model, id));
    }
}
