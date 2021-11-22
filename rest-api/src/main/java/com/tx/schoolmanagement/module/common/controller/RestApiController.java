package com.tx.schoolmanagement.module.common.controller;

import com.tx.schoolmanagement.module.common.mapper.Mapper;
import com.tx.schoolmanagement.module.common.service.CrudService;
import com.tx.schoolmanagement.module.common.service.Model;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.stream.Collectors;

import static com.tx.schoolmanagement.module.common.constant.ResponseConstants.CREATED;
import static com.tx.schoolmanagement.module.common.constant.ResponseConstants.REMOVED;
import static com.tx.schoolmanagement.module.common.constant.ResponseConstants.UPDATED;
import static com.tx.schoolmanagement.module.common.controller.ResultStatus.SUCCESS;

/**
 * Base controller to manage communication between http requests and services.
 *
 * @param <K> model id.
 * @param <T> model dto.
 * @param <U> model.
 */
public abstract class RestApiController<K, T, U extends Model<K>> {

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
     * @param page current page of the pagination.
     * @param size total item per page.
     * @return a list of dto.
     */
    @GetMapping
    public ResponseEntity<ResultPage<T>> getAll(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "15") int size) {
        Page<U> modelPage = modelService.readAll(PageRequest.of(page, size));
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
        U model = mapper.toModel(dto);
        modelService.create(model);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new ResultInfo(SUCCESS, String.format(CREATED, getModelName(), model.getId())));
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
        return ResponseEntity.ok(new ResultInfo(SUCCESS, String.format(UPDATED, getModelName(), id)));
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
        return ResponseEntity.ok(new ResultInfo(SUCCESS, String.format(REMOVED, getModelName(), id)));
    }

    protected abstract String getModelName();
}