package com.truextend.problem1.module.common.service;

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
     * Reads all models.
     *
     * @return the list of models.
     */
    List<T> readAll();

    /**
     * Reads all models with specific filter.
     *
     * @param queryParams request query param.
     * @return the list of models.
     */
    List<T> readAll(Map<String, String> queryParams);

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
