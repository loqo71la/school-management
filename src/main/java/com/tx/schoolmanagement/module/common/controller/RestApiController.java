package com.tx.schoolmanagement.module.common.controller;

import com.tx.schoolmanagement.module.common.constant.ControllerConstants;
import com.tx.schoolmanagement.module.common.exception.BadRequestException;
import com.tx.schoolmanagement.module.common.mapper.Mapper;
import com.tx.schoolmanagement.module.common.service.CrudService;
import com.tx.schoolmanagement.module.common.utils.RequestUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
     * Store the mapper instance.
     */
    protected Mapper<T, U> mapper;

    /**
     * HTTP GetAll method.
     *
     * @param queryParams Request query param
     * @return a list of dto.
     */
    @GetMapping
    public ResponseEntity<ResultPage<T>> getAll(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "15") int size,
                                                @RequestParam Map<String, String> queryParams) {
        RequestUtil.validateFields(queryParams, mapper.getDtoFields());
        Page<U> modelPage = modelService.readAll(queryParams, PageRequest.of(page, size));
        return ResponseEntity.ok(new ResultPage<>(
            (int) modelPage.getTotalElements(),
            modelPage.getTotalPages(),
            modelPage.getNumber(),
            modelPage.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList())
        ));
    }

    /**
     * HTTP GetById method.
     *
     * @param id of selected dto.
     * @return a single dto.
     */
    @GetMapping("/{id}")
    public ResponseEntity<T> getById(@PathVariable K id) {
        T dto = mapper.toDto(modelService.read(id));
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
        modelService.create(mapper.toModel(dto));
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
        modelService.update(id, mapper.toModel(dto));
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
     * Builds a success result info.
     *
     * @return the result info.
     */
    private ResultInfo buildResultInfo() {
        return new ResultInfo(ResultStatus.SUCCESS);
    }
}