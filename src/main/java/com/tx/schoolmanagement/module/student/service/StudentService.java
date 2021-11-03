package com.tx.schoolmanagement.module.student.service;

import com.tx.schoolmanagement.module.common.service.CrudService;
import com.tx.schoolmanagement.module.student.repository.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface StudentService extends CrudService<String, Student> {

    Page<Student> readAllByClazz(String clazzCode, Map<String, String> queryParams, Pageable pageable);

    void assignClazz(String studentId, String clazzCode);

    void unassignClazz(String studentId, String clazzCode);
}
