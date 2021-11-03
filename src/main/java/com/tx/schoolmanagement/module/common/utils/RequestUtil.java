package com.tx.schoolmanagement.module.common.utils;

import com.tx.schoolmanagement.module.common.constant.ControllerConstants;
import com.tx.schoolmanagement.module.common.exception.BadRequestException;

import java.util.List;
import java.util.Map;
import java.util.Set;

public final class RequestUtil {

    private RequestUtil() {
    }

    public static void validateFields(Map<String, String> queryParams, List<String> defaultFields) {
        Set<String> fields = queryParams.keySet();
        if (!defaultFields.containsAll(fields)) {
            defaultFields.forEach(fields::remove);
            throw new BadRequestException(String.format(ControllerConstants.INVALID_FILTER_ERROR, fields));
        }
    }
}
