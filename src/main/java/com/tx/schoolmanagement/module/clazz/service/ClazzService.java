package com.tx.schoolmanagement.module.clazz.service;

import com.tx.schoolmanagement.module.clazz.repository.Clazz;
import com.tx.schoolmanagement.module.common.service.CrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface ClazzService extends CrudService<String, Clazz> {

    Page<Clazz> readAllByStudent(String studentId, Map<String, String> queryParams, Pageable pageable);

    void assignStudent(String clazzCode, String studentId);

    void unassignStudent(String clazzCode, String studentId);
}
