package com.truextend.problem1.module.student.controller;

import com.truextend.problem1.module.common.constant.ControllerConstants;
import com.truextend.problem1.module.common.constant.DtoFieldConstants;
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
public class StudentController extends RestApiController<Integer, StudentDto, Student> {

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
     * Returns the list of StudentDto field names.
     *
     * @return the list of fields.
     */
    @Override
    protected List<String> getDtoFields() {
        return Arrays.asList(DtoFieldConstants.STUDENT_ID,
                DtoFieldConstants.STUDENT_NAME,
                DtoFieldConstants.STUDENT_LAST_NAME);
    }

    /**
     * Converts student to studentDto.
     *
     * @param student to be converted.
     * @return a studentDto.
     */
    @Override
    protected StudentDto toDto(Student student) {
        StudentDto studentDto = new StudentDto();
        studentDto.setId(student.getId());
        studentDto.setName(student.getName());
        studentDto.setLastName(student.getLastName());
        return studentDto;
    }

    /**
     * Converts studentDto to student.
     *
     * @param studentDto to be converted.
     * @return a student.
     */
    @Override
    protected Student toModel(StudentDto studentDto) {
        Student student = new Student();
        student.setId(studentDto.getId());
        student.setName(studentDto.getName());
        student.setLastName(studentDto.getLastName());
        return student;
    }
}
