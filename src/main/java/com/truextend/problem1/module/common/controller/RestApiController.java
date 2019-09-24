package com.truextend.problem1.module.common.controller;

import com.truextend.problem1.module.common.constant.ControllerConstants;
import com.truextend.problem1.module.common.exception.BadRequestException;
import com.truextend.problem1.module.common.service.CrudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Base controller to manage communication between http requests and services.
 *
 * @param <K> model id.
 * @param <T> model dto.
 * @param <U> model.
 */
public abstract class RestApiController<K, T, U> {

    /**
     * Stores the crud service instance.
     */
    protected CrudService<K, U> modelService;

    /**
     * HTTP GetAll method.
     *
     * @param queryParams Request query param
     * @return a list of dto.
     */
    @GetMapping
    public ResponseEntity<List<T>> getAll(@RequestParam Map<String, String> queryParams) {
        Set<String> fields = queryParams.keySet();
        if (!hasValidFields(fields)) {
            throw new BadRequestException(buildErrorMessage(fields));
        }

        List<T> dtoList = modelService.readAll(queryParams)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    /**
     * HTTP GetById method.
     *
     * @param id of selected dto.
     * @return a single dto.
     */
    @GetMapping("/{id}")
    public ResponseEntity<T> getById(@PathVariable K id) {
        T dto = toDto(modelService.read(id));
        return ResponseEntity.ok(dto);
    }

    /**
     * HTTP Post method.
     *
     * @param dto to be saved.
     * @return the details of the processed request.
     */
    @PostMapping
    public ResponseEntity<ResultInfo> post(@RequestBody T dto) {
        modelService.create(toModel(dto));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(buildResultInfo());
    }

    /**
     * HTTP Put method.
     *
     * @param id  of selected dto.
     * @param dto to be updated.
     * @return the details of the processed request.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ResultInfo> put(@PathVariable K id, @RequestBody T dto) {
        modelService.update(id, toModel(dto));
        return ResponseEntity.ok(buildResultInfo());
    }

    /**
     * HTTP Delete method.
     *
     * @param id of selected dto.
     * @return the details of the processed request.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResultInfo> delete(@PathVariable K id) {
        modelService.delete(id);
        return ResponseEntity.ok(buildResultInfo());
    }

    /**
     * Returns the list of dto fields.
     *
     * @return the list of fields.
     */
    protected abstract List<String> getDtoFields();

    /**
     * Converts model to dto.
     *
     * @param model to be converted.
     * @return a dto.
     */
    protected abstract T toDto(U model);

    /**
     * Converts dto to model.
     *
     * @param dto to be converted.
     * @return a model.
     */
    protected abstract U toModel(T dto);

    /**
     * Verifies if the selected fields are valid.
     *
     * @param fields to be validated.
     * @return true if are valid, otherwise false.
     */
    private boolean hasValidFields(Set<String> fields) {
        return getDtoFields().containsAll(fields);
    }

    /**
     * Builds the error message for invalid fields.
     *
     * @param fields to be validated.
     * @return the error message.
     */
    private String buildErrorMessage(Set<String> fields) {
        fields.removeAll(getDtoFields());
        return String.format(ControllerConstants.INVALID_FILTER_ERROR, fields.toString());
    }

    /**
     * Builds a success result info.
     *
     * @return the result info.
     */
    private ResultInfo buildResultInfo() {
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setStatus(ResultStatus.SUCCESS);
        return resultInfo;
    }
}