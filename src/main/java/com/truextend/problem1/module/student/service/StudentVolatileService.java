package com.truextend.problem1.module.student.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.truextend.problem1.module.common.exception.ItemNotFoundException;
import com.truextend.problem1.module.common.exception.UnauthorizedException;
import com.truextend.problem1.module.student.controller.StudentJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@Service
public class StudentVolatileService implements StudentService {

    private static final String UNAUTHORIZED_ERROR = "Student already exists";

    private static final String NOT_FOUND_ERROR = "Student not found";

    @Autowired
    private Map<Integer, Student> volatileStudents;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public StudentJson create(StudentJson studentJson) {
        Integer studentId = studentJson.getId();
        if (volatileStudents.containsKey(studentId)) {
            throw new UnauthorizedException(UNAUTHORIZED_ERROR);
        }

        if (studentId == null) {
            studentId = volatileStudents.keySet()
                    .stream()
                    .max(comparing(id -> id))
                    .orElse(volatileStudents.size());
            studentJson.setId(++studentId);
        }
        volatileStudents.put(studentId, toStudent(studentJson));
        return studentJson;
    }

    @Override
    public List<StudentJson> readAll() {
        return readAll(Collections.emptyMap());
    }

    @Override
    public List<StudentJson> readAll(Map<String, String> queryParams) {
        Predicate<Student> matcher = student -> Boolean.TRUE;
        if (queryParams != null && !queryParams.isEmpty()) {
            StudentJson studentJson = objectMapper.convertValue(queryParams, StudentJson.class);
            matcher = student -> match(student, studentJson);
        }
        return volatileStudents.values()
                .stream()
                .filter(matcher)
                .sorted(comparing(Student::getId))
                .map(this::toStudentJson)
                .collect(Collectors.toList());
    }

    @Override
    public StudentJson read(Integer id) {
        if (!volatileStudents.containsKey(id)) {
            throw new ItemNotFoundException(NOT_FOUND_ERROR);
        }
        return toStudentJson(volatileStudents.get(id));
    }

    @Override
    public void update(Integer id, StudentJson studentJson) {
        if (!volatileStudents.containsKey(id)) {
            throw new ItemNotFoundException(NOT_FOUND_ERROR);
        }
        studentJson.setId(id);
        volatileStudents.put(id, toStudent(studentJson));
    }

    @Override
    public void delete(Integer id) {
        volatileStudents.remove(id);
    }

    private boolean match(Student student, StudentJson studentJson) {
        boolean isMatchedName = Boolean.FALSE;
        if (studentJson.getName() != null) {
            isMatchedName = student.getName()
                    .toLowerCase()
                    .contains(studentJson.getName());
        }

        boolean isMatchedLastName = Boolean.FALSE;
        if (studentJson.getLastName() != null) {
            isMatchedLastName = student.getLastName()
                    .toLowerCase()
                    .contains(studentJson.getLastName());
        }
        return isMatchedName || isMatchedLastName;
    }

    private StudentJson toStudentJson(Student student) {
        StudentJson studentJson = new StudentJson();
        studentJson.setId(student.getId());
        studentJson.setName(student.getName());
        studentJson.setLastName(student.getLastName());
        return studentJson;
    }

    private Student toStudent(StudentJson studentJson) {
        Student student = new Student();
        student.setId(studentJson.getId());
        student.setName(studentJson.getName());
        student.setLastName(studentJson.getLastName());
        return student;
    }
}
