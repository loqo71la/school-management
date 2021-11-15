package com.tx.schoolmanagement.module.common.constant;

/**
 * Constants for Response Messages.
 */
public final class ResponseConstants {

    /**
     * Default Constructor.
     */
    private ResponseConstants() {
    }

    public static final String UNASSIGNED = "%s with ID [%s] was unassigned from %s with ID [%s].";
    public static final String ASSIGNED = "%s with ID [%s] was assigned to %s with ID [%s].";
    public static final String CREATED = "%s with ID [%s] was successfully created.";
    public static final String UPDATED = "%s with ID [%s] was successfully updated.";
    public static final String REMOVED = "%s with ID [%s] was successfully removed.";

    public static final String ALREADY_EXIST = "%s with ID [%s] already exist.";
    public static final String NOT_FOUND = "%s with ID [%s] was not found.";
    public static final String BAD_REQUEST = "The request could not be understood by the server due to malformed syntax";

}
