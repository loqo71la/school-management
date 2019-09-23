package com.truextend.problem1.module.student.controller;

import com.truextend.problem1.module.common.ResultInfo;
import com.truextend.problem1.module.common.ResultStatus;
import com.truextend.problem1.module.common.exception.BadRequestException;
import com.truextend.problem1.module.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private static final String ERROR_MESSAGE = "Invalid filters: %s";

    @Autowired
    private StudentService studentService;

    @GetMapping
    public ResponseEntity<List<StudentJson>> getAll(@RequestParam Map<String, String> queryParams) {
        Set<String> fields = queryParams.keySet();
        if (!hasValidFields(fields)) {
            throw new BadRequestException(buildErrorMessage(fields));
        }
        return ResponseEntity.ok(studentService.readAll(queryParams));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentJson> getById(@PathVariable int id) {
        return ResponseEntity.ok(studentService.read(id));
    }

    @PostMapping
    public ResponseEntity<ResultInfo> post(@RequestBody StudentJson student) {
        studentService.create(student);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(buildResultInfo());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultInfo> put(@PathVariable int id, @RequestBody StudentJson student) {
        studentService.update(id, student);
        return ResponseEntity.ok(buildResultInfo());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResultInfo> delete(@PathVariable int id) {
        studentService.delete(id);
        return ResponseEntity.ok(buildResultInfo());
    }

    private boolean hasValidFields(Set<String> fields) {
        return StudentJson.FIELDS
                .containsAll(fields);
    }

    private String buildErrorMessage(Set<String> fields) {
        fields.removeAll(StudentJson.FIELDS);
        return String.format(ERROR_MESSAGE, fields.toString());
    }

    private ResultInfo buildResultInfo() {
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setStatus(ResultStatus.SUCCESS);
        return resultInfo;
    }
}
