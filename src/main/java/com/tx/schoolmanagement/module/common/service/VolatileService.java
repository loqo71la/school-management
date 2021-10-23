package com.tx.schoolmanagement.module.common.service;

import com.tx.schoolmanagement.module.common.exception.ItemNotFoundException;
import com.tx.schoolmanagement.module.common.exception.UnauthorizedException;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class VolatileService<K, T extends Model<K>> implements CrudService<K, T> {

    /**
     * Stores the error message for unauthorized.
     */
    protected String unauthorizedErrorMessage;

    /**
     * Stores the error message for not found.
     */
    protected String notFoundErrorMessage;

    /**
     * Stores the volatile data in the system.
     */
    protected Map<K, T> volatileData;

    /**
     * Creates a new model.
     *
     * @param model to be created.
     */
    @Override
    public void create(T model) {
        K modelId = model.getId();
        if (volatileData.containsKey(modelId)) {
            throw new UnauthorizedException(unauthorizedErrorMessage);
        }

        if (modelId == null) {
            modelId = findLastId();
            model.setId(modelId);
        }
        volatileData.put(modelId, model);
    }

    @Override
    public List<T> readAll() {
        return readAll(Collections.emptyMap());
    }

    @Override
    public List<T> readAll(Map<String, String> queryParams) {
        return volatileData.values()
                .stream()
                .sorted()
                .filter(buildFilter(queryParams))
                .collect(Collectors.toList());
    }

    @Override
    public T read(K id) {
        if (!volatileData.containsKey(id)) {
            throw new ItemNotFoundException(notFoundErrorMessage);
        }
        return volatileData.get(id);
    }

    @Override
    public void update(K id, T model) {
        if (!volatileData.containsKey(id)) {
            throw new ItemNotFoundException(notFoundErrorMessage);
        }
        model.setId(id);
        volatileData.put(id, model);
    }

    @Override
    public void delete(K id) {
        volatileData.remove(id);
    }

    protected abstract K findLastId();

    protected abstract boolean match(T model, Map<String, String> queryParams);

    private Predicate<T> buildFilter(Map<String, String> queryParams) {
        if (queryParams == null || queryParams.isEmpty()) {
            return student -> Boolean.TRUE;
        }
        return model -> match(model, queryParams);
    }
}
