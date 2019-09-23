package com.truextend.problem1.module.common.controller;

import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.util.StringUtils;

/**
 * Wrap the status result.
 */
public enum ResultStatus {

    /**
     * Stores the result status 'Success'.
     */
    SUCCESS,

    /**
     * Stores the result status 'Error'.
     */
    ERROR;

    /**
     * Returns the name of this status result.
     *
     * @return the string representation.
     */
    @JsonValue
    public String getName() {
        return name().toLowerCase();
    }
}
