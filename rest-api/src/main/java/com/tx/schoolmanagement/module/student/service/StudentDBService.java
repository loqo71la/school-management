package com.tx.schoolmanagement.module.student.service;

import com.tx.schoolmanagement.module.clazz.service.ClazzService;
import com.tx.schoolmanagement.module.common.service.DBService;
import com.tx.schoolmanagement.module.student.repository.Student;
import com.tx.schoolmanagement.module.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.tx.schoolmanagement.module.common.constant.DtoConstants.STUDENT_MODEL;

@Service
public class StudentDBService extends DBService<String, Student> implements StudentService {

    @Autowired
    private ClazzService clazzService;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Page<Student> readPageByClazz(String clazzCode, Pageable pageable) {
        return studentRepository.findPageByClazz(clazzCode, pageable);
    }

    @Override
    public void update(String studentId, Student newStudent) {
        Student student = super.read(studentId);
        student.setLastname(newStudent.getLastname());
        student.setGender(newStudent.getGender());
        student.setName(newStudent.getName());
        student.setModifiedDate(new Date());
        studentRepository.save(student);
    }

    @Override
    public void delete(String studentId) {
        clazzService.readAllByStudent(studentId)
            .forEach(clazz -> clazzService.unassignStudent(clazz.getId(), studentId));
        studentRepository.deleteById(studentId);
    }

    @Override
    public void assignClazz(String studentId, String clazzCode) {
        clazzService.assignStudent(clazzCode, studentId);
    }

    @Override
    public void unassignClazz(String studentId, String clazzCode) {
        clazzService.unassignStudent(clazzCode, studentId);
    }

    @Override
    protected PagingAndSortingRepository<Student, String> getModelRepository() {
        return studentRepository;
    }

    @Override
    protected String getModelName() {
        return STUDENT_MODEL;
    }
}
