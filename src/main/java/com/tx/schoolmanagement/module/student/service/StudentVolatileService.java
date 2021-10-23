package com.tx.schoolmanagement.module.student.service;

import com.tx.schoolmanagement.module.common.constant.DtoFieldConstants;
import com.tx.schoolmanagement.module.common.service.VolatileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import static java.util.Comparator.comparing;

@Service
public class StudentVolatileService extends VolatileService<Integer, Student> {

    /**
     * Stores the error message for unauthorized.
     */
    private static final String UNAUTHORIZED_ERROR = "Student already exists";

    /**
     * Stores the error message for not found.
     */
    private static final String NOT_FOUND_ERROR = "Student not found";

    /**
     * Default Constructor to load properties.
     */
    public StudentVolatileService() {
        super.unauthorizedErrorMessage = UNAUTHORIZED_ERROR;
        super.notFoundErrorMessage = NOT_FOUND_ERROR;
    }

    /**
     * Auto injects the volatile students in the system.
     *
     * @param volatileStudents to be set.
     */
    @Autowired
    public void setVolatileStudents(Map<Integer, Student> volatileStudents) {
        super.volatileData = volatileStudents;
    }

    @Override
    public boolean match(Student student, Map<String, String> queryParams) {
        boolean isMatchedName = Boolean.FALSE;
        if (queryParams.containsKey(DtoFieldConstants.STUDENT_NAME)) {
            isMatchedName = student.getName()
                    .toLowerCase()
                    .contains(queryParams.get(DtoFieldConstants.STUDENT_NAME));
        }

        boolean isMatchedLastName = Boolean.FALSE;
        if (queryParams.containsKey(DtoFieldConstants.STUDENT_LAST_NAME)) {
            isMatchedLastName = student.getLastName()
                    .toLowerCase()
                    .contains(queryParams.get(DtoFieldConstants.STUDENT_LAST_NAME));
        }
        return isMatchedName || isMatchedLastName;
    }

    @Override
    protected Integer findLastId() {
        int lastId = super.volatileData
                .keySet()
                .stream()
                .max(comparing(id -> id))
                .orElse(super.volatileData.size());
        return ++lastId;
    }
}
