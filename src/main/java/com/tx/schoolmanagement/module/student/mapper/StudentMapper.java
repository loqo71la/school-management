package com.tx.schoolmanagement.module.student.mapper;

import com.tx.schoolmanagement.module.common.mapper.Mapper;
import com.tx.schoolmanagement.module.student.controller.StudentDto;
import com.tx.schoolmanagement.module.student.repository.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper implements Mapper<StudentDto, Student> {

    /**
     * Converts student to studentDto.
     *
     * @param student to be converted.
     * @return a studentDto.
     */
    @Override
    public StudentDto toDto(Student student) {
        return new StudentDto(
            student.getId(),
            student.getName(),
            student.getLastname(),
            student.getCreatedDate(),
            student.getModifiedDate(),
            student.getGender()
        );
    }

    /**
     * Converts studentDto to student.
     *
     * @param studentDto to be converted.
     * @return a student.
     */
    @Override
    public Student toModel(StudentDto studentDto) {
        Student student = new Student();
        student.setId(studentDto.id());
        student.setName(studentDto.name());
        student.setLastname(studentDto.lastname());
        student.setGender(studentDto.gender());
        return student;
    }
}
