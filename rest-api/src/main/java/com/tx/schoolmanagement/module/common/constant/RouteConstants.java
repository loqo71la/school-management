package com.tx.schoolmanagement.module.common.constant;

/**
 * Constants for end-point
 */
public final class RouteConstants {

    /**
     * Default Constructor.
     */
    private RouteConstants() {
    }

    /**
     * Students end-point.
     */
    public static final String STUDENT_URL = "/api/students";

    /**
     * Classes end-point.
     */
    public static final String CLAZZ_URL = "/api/classes";

    /**
     * Students by class end-point.
     */
    public static final String STUDENT_BY_CLAZZ_URL = "/api/classes/{clazzCode}/students";

    /**
     * Classes by student end-point.
     */
    public static final String CLAZZ_BY_STUDENT_URL = "/api/students/{studentId}/classes";
}