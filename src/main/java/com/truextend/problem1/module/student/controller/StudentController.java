package com.truextend.problem1.module.student.controller;

import com.truextend.problem1.module.common.constant.ControllerConstants;
import com.truextend.problem1.module.common.constant.JsonFieldConstants;
import com.truextend.problem1.module.common.controller.RestApiController;
import com.truextend.problem1.module.student.service.Student;
import com.truextend.problem1.module.student.service.StudentVolatileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * Manages HTTP request for students.
 */
@RestController
@RequestMapping(ControllerConstants.STUDENT_URL)
public class StudentController extends RestApiController<Integer, StudentJson, Student> {

    /**
     * Auto injects a proper service.
     *
     * @param studentService CrudService implementation for students.
     */
    @Autowired
    public void setService(StudentVolatileService studentService) {
        super.modelService = studentService;
    }

    /**
     * Returns the list of StudentJson field names.
     *
     * @return the list of fields.
     */
    @Override
    protected List<String> getJsonFields() {
        return Arrays.asList(JsonFieldConstants.STUDENT_ID,
                JsonFieldConstants.STUDENT_NAME,
                JsonFieldConstants.STUDENT_LAST_NAME);
    }

    /**
     * Converts to studentJson a specific student.
     *
     * @param student to be converted.
     * @return a studentJson.
     */
    @Override
    protected StudentJson toJson(Student student) {
        StudentJson studentJson = new StudentJson();
        studentJson.setId(student.getId());
        studentJson.setName(student.getName());
        studentJson.setLastName(student.getLastName());
        return studentJson;
    }

    /**
     * Converts to student a specific studentJson.
     *
     * @param studentJson to be converted.
     * @return a student.
     */
    @Override
    protected Student toModel(StudentJson studentJson) {
        Student student = new Student();
        student.setId(studentJson.getId());
        student.setName(studentJson.getName());
        student.setLastName(studentJson.getLastName());
        return student;
    }
}
