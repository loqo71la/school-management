package com.tx.schoolmanagement.module.student.service;

import com.tx.schoolmanagement.module.clazz.service.ClazzService;
import com.tx.schoolmanagement.module.common.service.DBService;
import com.tx.schoolmanagement.module.student.repository.Student;
import com.tx.schoolmanagement.module.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class StudentDBService extends DBService<String, Student> implements StudentService {

    @Autowired
    private ClazzService clazzService;

    @Autowired
    public void setModelRepository(StudentRepository studentRepository) {
        super.modelRepository = studentRepository;
    }

    @Override
    public Page<Student> readAll(Map<String, String> queryParams, Pageable pageable) {
        String name = queryParams.get("name");
        String lastname = queryParams.get("lastname");

        StudentRepository studentRepository = getStudentRepository();
        if (name != null && lastname != null) {
            return studentRepository.findAllByNameAndLastname(name, lastname, pageable);
        }
        return lastname != null ?
            studentRepository.findAllByLastname(lastname, pageable) :
            name != null ?
                studentRepository.findAllByName(name, pageable) :
                studentRepository.findAll(pageable);
    }

    @Override
    public Page<Student> readAllByClazz(String clazzCode, Map<String, String> queryParams, Pageable pageable) {
        return getStudentRepository().findAllByClazz(clazzCode, pageable);
    }

    @Override
    public void update(String id, Student newStudent) {
        Student student = super.read(id);
        student.setName(newStudent.getName());
        student.setLastname(newStudent.getLastname());
        student.setGender(newStudent.getGender());
        student.setModifiedDate(new Date());
        super.modelRepository.save(student);
    }

    @Override
    public void assignClazz(String studentId, String clazzCode) {
        clazzService.assignStudent(clazzCode, studentId);
    }

    @Override
    public void unassignClazz(String studentId, String clazzCode) {
        clazzService.unassignStudent(clazzCode, studentId);
    }

    private StudentRepository getStudentRepository() {
        return (StudentRepository) super.modelRepository;
    }
}
