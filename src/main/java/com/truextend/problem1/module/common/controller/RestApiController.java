package com.truextend.problem1.module.common.controller;

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
 * @param <K> represents an id.
 * @param <T> represents a serialized json.
 * @param <U> represents a model.
 */
public abstract class RestApiController<K, T, U> {

    /**
     * Stores the error message for invalid filters.
     */
    private static final String INVALID_FILTER_ERROR = "Invalid filters: %s";

    /**
     * Stores the crud service instance.
     */
    protected CrudService<K, U> modelService;

    /**
     * HTTP GetAll method.
     *
     * @param queryParams Request query param
     * @return a list of json
     */
    @GetMapping
    public ResponseEntity<List<T>> getAll(@RequestParam Map<String, String> queryParams) {
        Set<String> fields = queryParams.keySet();
        if (!hasValidFields(fields)) {
            throw new BadRequestException(buildErrorMessage(fields));
        }

        List<T> jsonList = modelService.readAll(queryParams)
                .stream()
                .map(this::toJson)
                .collect(Collectors.toList());
        return ResponseEntity.ok(jsonList);
    }

    /**
     * HTTP GetById method.
     *
     * @param id of selected json.
     * @return a single json.
     */
    @GetMapping("/{id}")
    public ResponseEntity<T> getById(@PathVariable K id) {
        T json = toJson(modelService.read(id));
        return ResponseEntity.ok(json);
    }

    /**
     * HTTP Post method.
     *
     * @param json to be saved.
     * @return the details of the processed request.
     */
    @PostMapping
    public ResponseEntity<ResultInfo> post(@RequestBody T json) {
        modelService.create(toModel(json));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(buildResultInfo());
    }

    /**
     * HTTP Put method.
     *
     * @param id   of selected json.
     * @param json to be updated.
     * @return the details of the processed request.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ResultInfo> put(@PathVariable K id, @RequestBody T json) {
        modelService.update(id, toModel(json));
        return ResponseEntity.ok(buildResultInfo());
    }

    /**
     * HTTP Delete method.
     *
     * @param id of selected json.
     * @return the details of the processed request.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResultInfo> delete(@PathVariable K id) {
        modelService.delete(id);
        return ResponseEntity.ok(buildResultInfo());
    }

    /**
     * Returns the list of json fields.
     *
     * @return the list of fields.
     */
    protected abstract List<String> getJsonFields();

    /**
     * Converts to json a specific model.
     *
     * @param model to be converted.
     * @return a model.
     */
    protected abstract T toJson(U model);

    /**
     * Converts to model a specific json.
     *
     * @param json to be converted.
     * @return a json.
     */
    protected abstract U toModel(T json);

    /**
     * Verifies if the selected fields are valid.
     *
     * @param fields to be validated.
     * @return true if are valid, otherwise false.
     */
    private boolean hasValidFields(Set<String> fields) {
        return getJsonFields().containsAll(fields);
    }

    /**
     * Builds the error message for invalid fields.
     *
     * @param fields to be validated.
     * @return the error message.
     */
    private String buildErrorMessage(Set<String> fields) {
        fields.removeAll(getJsonFields());
        return String.format(INVALID_FILTER_ERROR, fields.toString());
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