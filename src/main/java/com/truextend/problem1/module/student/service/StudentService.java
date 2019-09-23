package com.truextend.problem1.module.student.service;

import com.truextend.problem1.module.common.CrudService;
import com.truextend.problem1.module.student.controller.StudentJson;

import java.util.List;
import java.util.Map;

public interface StudentService extends CrudService<StudentJson, Integer> {

    List<StudentJson> readAll(Map<String, String> queryParams);
}
