package com.tx.schoolmanagement.module.clazz.controller;

import com.tx.schoolmanagement.module.clazz.mapper.ClazzMapper;
import com.tx.schoolmanagement.module.clazz.repository.Clazz;
import com.tx.schoolmanagement.module.clazz.service.ClazzService;
import com.tx.schoolmanagement.module.common.constant.ControllerConstants;
import com.tx.schoolmanagement.module.common.controller.ResultInfo;
import com.tx.schoolmanagement.module.common.controller.ResultPage;
import com.tx.schoolmanagement.module.common.controller.ResultStatus;
import com.tx.schoolmanagement.module.common.utils.RequestUtil;
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
 * Manages HTTP request for clazzes by student.
 */
@RestController
@RequestMapping(ControllerConstants.CLAZZ_BY_STUDENT_URL)
public class ClazzByStudentController {

    /**
     * Stores the clazz service.
     */
    @Autowired
    private ClazzService clazzService;

    /**
     * Stores the clazz mapper.
     */
    @Autowired
    private ClazzMapper clazzMapper;

    /**
     * HTTP GetAll method.
     *
     * @param studentId   id of selected student.
     * @param queryParams Request query param.
     * @return a list of studentDto.
     */
    @GetMapping
    public ResponseEntity<ResultPage<ClazzDto>> getAll(@PathVariable String studentId,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "15") int size,
                                                       @RequestParam Map<String, String> queryParams) {
        RequestUtil.validateFields(queryParams, clazzMapper.getDtoFields());
        Page<Clazz> modelPage = clazzService.readAllByStudent(studentId, queryParams, PageRequest.of(page, size));
        return ResponseEntity.ok(new ResultPage<>(
            (int) modelPage.getTotalElements(),
            modelPage.getTotalPages(),
            modelPage.getNumber(),
            modelPage.stream()
                .map(clazzMapper::toDto)
                .collect(Collectors.toList())
        ));
    }

    /**
     * HTTP Post method.
     *
     * @param clazzCode code of selected clazz.
     * @param studentId id of selected student.
     * @return the details of the processed request.
     */
    @PostMapping("/{clazzCode}")
    public ResponseEntity<ResultInfo> post(@PathVariable String studentId, @PathVariable String clazzCode) {
        clazzService.assignStudent(clazzCode, studentId);
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
    @DeleteMapping("/{clazzCode}")
    public ResponseEntity<ResultInfo> delete(@PathVariable String studentId, @PathVariable String clazzCode) {
        clazzService.unassignStudent(clazzCode, studentId);
        ResultInfo resultInfo = new ResultInfo(ResultStatus.SUCCESS);
        return ResponseEntity.ok(resultInfo);
    }
}
