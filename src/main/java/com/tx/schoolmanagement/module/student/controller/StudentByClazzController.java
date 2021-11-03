package com.tx.schoolmanagement.module.student.controller;

import com.tx.schoolmanagement.module.common.constant.ControllerConstants;
import com.tx.schoolmanagement.module.common.controller.ResultInfo;
import com.tx.schoolmanagement.module.common.controller.ResultPage;
import com.tx.schoolmanagement.module.common.controller.ResultStatus;
import com.tx.schoolmanagement.module.common.utils.RequestUtil;
import com.tx.schoolmanagement.module.student.mapper.StudentMapper;
import com.tx.schoolmanagement.module.student.repository.Student;
import com.tx.schoolmanagement.module.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Manages HTTP request for students by clazz.
 */
@RestController
@RequestMapping(ControllerConstants.STUDENT_BY_CLAZZ_URL)
public class StudentByClazzController {

    /**
     * Stores the clazz volatile service.
     */
    @Autowired
    private StudentService studentService;

    /**
     * Stores the student mapper.
     */
    @Autowired
    private StudentMapper studentMapper;

    /**
     * HTTP GetAll method.
     *
     * @param clazzCode   code of selected clazz.
     * @param queryParams Request query param.
     * @return a list of studentDto.
     */
    @GetMapping
    public ResponseEntity<ResultPage<StudentDto>> getAll(@PathVariable String clazzCode,
                                                         @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "15") int size,
                                                         @RequestParam Map<String, String> queryParams) {
        RequestUtil.validateFields(queryParams, studentMapper.getDtoFields());
        Page<Student> modelPage = studentService.readAllByClazz(clazzCode, queryParams, PageRequest.of(page, size));
        return ResponseEntity.ok(new ResultPage<>(
            (int) modelPage.getTotalElements(),
            modelPage.getTotalPages(),
            modelPage.getNumber(),
            modelPage.stream()
                .map(studentMapper::toDto)
                .collect(Collectors.toList())
        ));
    }

    /**
     * HTTP Put method.
     *
     * @param clazzCode code of selected clazz.
     * @param studentId id of selected student.
     * @return the details of the processed request.
     */
    @PostMapping("/{studentId}")
    public ResponseEntity<ResultInfo> post(@PathVariable String clazzCode, @PathVariable String studentId) {
        studentService.assignClazz(studentId, clazzCode);
        ResultInfo resultInfo = new ResultInfo(ResultStatus.SUCCESS);
        return ResponseEntity.ok(resultInfo);
    }

    /**
     * HTTP Delete method.
     *
     * @param clazzCode code of selected clazz.
     * @param studentId id of selected student.
     * @return the details of the processed request.
     */
    @DeleteMapping("/{studentId}")
    public ResponseEntity<ResultInfo> delete(@PathVariable String clazzCode, @PathVariable String studentId) {
        studentService.unassignClazz(studentId, clazzCode);
        ResultInfo resultInfo = new ResultInfo(ResultStatus.SUCCESS);
        return ResponseEntity.ok(resultInfo);
    }
}
