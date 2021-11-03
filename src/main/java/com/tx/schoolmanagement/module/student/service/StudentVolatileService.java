package com.tx.schoolmanagement.module.student.service;

import com.tx.schoolmanagement.module.common.constant.DtoFieldConstants;
import com.tx.schoolmanagement.module.common.service.VolatileService;
import com.tx.schoolmanagement.module.student.repository.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

import static java.util.Comparator.comparing;

public class StudentVolatileService extends VolatileService<String, Student> implements StudentService {

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
    public void setVolatileStudents(Map<String, Student> volatileStudents) {
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

        boolean isMatchedLastname = Boolean.FALSE;
        if (queryParams.containsKey(DtoFieldConstants.STUDENT_LAST_NAME)) {
            isMatchedLastname = student.getLastname()
                .toLowerCase()
                .contains(queryParams.get(DtoFieldConstants.STUDENT_LAST_NAME));
        }
        return isMatchedName || isMatchedLastname;
    }

    @Override
    protected String findLastId() {
        String lastId = super.volatileData
            .keySet()
            .stream()
            .max(comparing(id -> id))
            .orElse(String.valueOf(super.volatileData.size()));
        return lastId;
    }

    @Override
    public void assignClazz(String studentId, String clazzCode) {
    }

    @Override
    public void unassignClazz(String studentId, String clazzCode) {
    }

    @Override
    public Page<Student> readAllByClazz(String clazzCode, Map<String, String> queryParams, Pageable pageable) {
        return null;
    }
}
