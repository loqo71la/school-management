package com.tx.schoolmanagement.module.student.controller;

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
 * Manages HTTP request for students by clazz.
 */
@RestController
@RequestMapping(ControllerConstants.STUDENT_BY_CLAZZ_URL)
public class StudentByClazzController {

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
     * @param clazzCode   code of selected clazz.
     * @param queryParams Request query param.
     * @return a list of studentDto.
     */
    @GetMapping
    public ResponseEntity<List<StudentDto>> getAll(@PathVariable String clazzCode, @RequestParam Map<String, String> queryParams) {
        Set<String> fieldList = queryParams.keySet();
        List<String> dtoFieldList = getDtoFields();
        if (!dtoFieldList.containsAll(fieldList)) {
            dtoFieldList.forEach(fieldList::remove);
            String errorMessage = String.format(ControllerConstants.INVALID_FILTER_ERROR, fieldList.toString());
            throw new BadRequestException(errorMessage);
        }

        Clazz clazz = clazzService.read(clazzCode);
        List<StudentDto> studentDtoList = clazz.getStudentIdList()
            .stream()
            .map(studentService::read)
            .filter(student -> queryParams.isEmpty() ? Boolean.TRUE : studentService.match(student, queryParams))
            .map(this::toDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok(studentDtoList);
    }

    /**
     * HTTP Put method.
     *
     * @param clazzCode code of selected clazz.
     * @param studentId id of selected student.
     * @return the details of the processed request.
     */
    @PutMapping("/{studentId}")
    public ResponseEntity<ResultInfo> put(@PathVariable String clazzCode, @PathVariable Integer studentId) {
        Clazz clazz = clazzService.read(clazzCode);
        Student student = studentService.read(studentId);
        clazz.addStudentId(studentId);
        student.addClazzCode(clazzCode);

        ResultInfo resultInfo = new ResultInfo(ResultStatus.SUCCESS);
        return ResponseEntity.ok(resultInfo);
    }

    /**
     * Returns the list of StudentDto field names.
     *
     * @return the list of fields.
     */
    private List<String> getDtoFields() {
        return Arrays.asList(DtoFieldConstants.STUDENT_ID,
            DtoFieldConstants.STUDENT_NAME,
            DtoFieldConstants.STUDENT_LAST_NAME);
    }

    /**
     * Converts student to studentDto.
     *
     * @param student to be converted.
     * @return a studentDto.
     */
    private StudentDto toDto(Student student) {
        return new StudentDto(
            student.getId(),
            student.getName(),
            student.getLastName()
        );
    }
}
