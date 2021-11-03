package com.tx.schoolmanagement.module.common.service;

import com.tx.schoolmanagement.module.common.exception.ItemNotFoundException;
import com.tx.schoolmanagement.module.common.exception.UnauthorizedException;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.Optional;

public abstract class DBService<K, T extends Model<K>> implements CrudService<K, T> {

    /**
     * Stores the error message for unauthorized.
     */
    protected String alreadyExistErrorMessage;

    /**
     * Stores the error message for not found.
     */
    protected String notFoundErrorMessage;

    /**
     * Stores the volatile data in the system.
     */
    protected CrudRepository<T, K> modelRepository;

    /**
     * Creates a new model.
     *
     * @param model to be created.
     */
    @Override
    public void create(T model) {
        Optional<T> current = readById(model.getId());
        if (current.isPresent()) {
            throw new UnauthorizedException(alreadyExistErrorMessage);
        }
        model.setCreatedDate(new Date());
        modelRepository.save(model);
    }

    @Override
    public T read(K id) {
        Optional<T> model = readById(id);
        if (model.isEmpty()) {
            throw new ItemNotFoundException(notFoundErrorMessage);
        }
        return model.get();
    }

    @Override
    public void delete(K id) {
        modelRepository.deleteById(id);
    }

    protected Optional<T> readById(K id) {
        if (id == null || id.toString().isEmpty()) {
            throw new ItemNotFoundException(notFoundErrorMessage);
        }
        return modelRepository.findById(id);
    }
}
