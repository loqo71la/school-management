package com.tx.schoolmanagement.module.common.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Defines the CRUD methods.
 *
 * @param <K> represents an id.
 * @param <T> represents a model.
 */
public interface CrudService<K, T> {

    /**
     * Creates a new model.
     *
     * @param model to be created.
     */
    void create(T model);

    /**
     * Reads all models with specific filter.
     *
     * @param queryParams request query param.
     * @param pageable request pagination fields.
     * @return the list of models.
     */
    Page<T> readAll(Map<String, String> queryParams, Pageable pageable);

    /**
     * Reads a single model for specific id.
     *
     * @param id model id.
     * @return the model.
     */
    T read(K id);

    /**
     * Updates a specific model.
     *
     * @param id    model id.
     * @param model to be updated.
     */
    void update(K id, T model);

    /**
     * Deletes and specific model.
     *
     * @param id model id
     */
    void delete(K id);
}
