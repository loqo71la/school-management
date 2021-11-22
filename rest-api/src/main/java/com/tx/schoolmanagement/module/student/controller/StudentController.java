package com.tx.schoolmanagement.module.student.controller;

import com.tx.schoolmanagement.module.common.constant.RouteConstants;
import com.tx.schoolmanagement.module.common.controller.RestApiController;
import com.tx.schoolmanagement.module.student.mapper.StudentMapper;
import com.tx.schoolmanagement.module.student.repository.Student;
import com.tx.schoolmanagement.module.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.tx.schoolmanagement.module.common.constant.DtoConstants.STUDENT_MODEL;

/**
 * Manages HTTP request for students.
 */
@RestController
@RequestMapping(RouteConstants.STUDENT_URL)
public class StudentController extends RestApiController<String, StudentDto, Student> {

    /**
     * Auto-injects a proper service.
     *
     * @param studentService CrudService implementation for students.
     */
    @Autowired
    public void setService(StudentService studentService) {
        super.modelService = studentService;
    }

    /**
     * Auto-injects a proper mapper.
     *
     * @param studentMapper StudentMapper implementation.
     */
    @Autowired
    public void setMapper(StudentMapper studentMapper) {
        super.mapper = studentMapper;
    }

    @Override
    protected String getModelName() {
        return STUDENT_MODEL;
    }
}
