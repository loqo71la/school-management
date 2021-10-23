package com.tx.schoolmanagement.module.clazz.controller;

import com.tx.schoolmanagement.module.clazz.service.Clazz;
import com.tx.schoolmanagement.module.clazz.service.ClazzVolatileService;
import com.tx.schoolmanagement.module.common.constant.ControllerConstants;
import com.tx.schoolmanagement.module.common.constant.DtoFieldConstants;
import com.tx.schoolmanagement.module.common.controller.ResultInfo;
import com.tx.schoolmanagement.module.common.controller.ResultStatus;
import com.tx.schoolmanagement.module.common.exception.BadRequestException;
import com.tx.schoolmanagement.module.student.service.Student;
import com.tx.schoolmanagement.module.student.service.StudentVolatileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Manages HTTP request for clazzes by student.
 */
@RestController
@RequestMapping(ControllerConstants.CLAZZ_BY_STUDENT_URL)
public class ClazzByStudentController {

    /**
     * Stores the student volatile service.
     */
    @Autowired
    private StudentVolatileService studentService;

    /**
     * Stores the clazz volatile service.
     */
    @Autowired
    private ClazzVolatileService clazzService;

    /**
     * HTTP GetAll method.
     *
     * @param studentId   id of selected student.
     * @param queryParams Request query param.
     * @return a list of studentDto.
     */
    @GetMapping
    public ResponseEntity<List<ClazzDto>> getAll(@PathVariable Integer studentId, @RequestParam Map<String, String> queryParams) {
        Set<String> fieldList = queryParams.keySet();
        List<String> dtoFieldList = getDtoFields();
        if (!dtoFieldList.containsAll(fieldList)) {
            dtoFieldList.forEach(fieldList::remove);
            String errorMessage = String.format(ControllerConstants.INVALID_FILTER_ERROR, fieldList.toString());
            throw new BadRequestException(errorMessage);
        }

        Student student = studentService.read(studentId);
        List<ClazzDto> clazzDtoList = student.getClazzCodeList()
            .stream()
            .map(clazzService::read)
            .filter(clazz -> queryParams.isEmpty() ? Boolean.TRUE : clazzService.match(clazz, queryParams))
            .map(this::toDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok(clazzDtoList);
    }

    /**
     * HTTP Put method.
     *
     * @param studentId id of selected studentDto.
     * @return the details of the processed request.
     */
    @PutMapping("/{clazzCode}")
    public ResponseEntity<ResultInfo> put(@PathVariable Integer studentId, @PathVariable String clazzCode) {
        Student student = studentService.read(studentId);
        Clazz clazz = clazzService.read(clazzCode);
        student.addClazzCode(clazzCode);
        clazz.addStudentId(studentId);

        ResultInfo resultInfo = new ResultInfo(ResultStatus.SUCCESS);
        return ResponseEntity.ok(resultInfo);
    }

    /**
     * Converts clazz to clazzDto.
     *
     * @param clazz to be converted.
     * @return a clazzDto.
     */
    private ClazzDto toDto(Clazz clazz) {
        return new ClazzDto(
            clazz.getId(),
            clazz.getTitle(),
            clazz.getDescription()
        );
    }

    /**
     * Returns the list of ClazzDto field names.
     *
     * @return the list of fields.
     */
    private List<String> getDtoFields() {
        return Arrays.asList(DtoFieldConstants.CLAZZ_CODE,
            DtoFieldConstants.CLAZZ_TITLE,
            DtoFieldConstants.CLAZZ_DESCRIPTION);
    }
}
