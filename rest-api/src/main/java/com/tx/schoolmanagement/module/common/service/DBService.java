package com.tx.schoolmanagement.module.common.service;

import com.tx.schoolmanagement.module.common.exception.AlreadyExistException;
import com.tx.schoolmanagement.module.common.exception.BadRequestException;
import com.tx.schoolmanagement.module.common.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.Optional;

public abstract class DBService<K, T extends Model<K>> implements CrudService<K, T> {

    /**
     * Reads all models with specific filter.
     *
     * @param pageable request pagination fields.
     * @return the list of models.
     */
    @Override
    public Page<T> readAll(Pageable pageable) {
        return getModelRepository().findAll(pageable);
    }

    /**
     * Creates a new model.
     *
     * @param model to be created.
     */
    @Override
    public void create(T model) {
        Optional<T> current = readById(model.getId());
        if (current.isPresent()) {
            throw new AlreadyExistException(getModelName(), String.valueOf(model.getId()));
        }
        model.setCreatedDate(new Date());
        getModelRepository().save(model);
    }

    /**
     * Reads a single model for specific id.
     *
     * @param id model id.
     * @return the model.
     */
    @Override
    public T read(K id) {
        Optional<T> model = readById(id);
        if (model.isEmpty()) {
            throw new NotFoundException(getModelName(), String.valueOf(id));
        }
        return model.get();
    }

    protected Optional<T> readById(K id) {
        if (id == null || id.toString().isEmpty()) {
            throw new BadRequestException();
        }
        return getModelRepository().findById(id);
    }

    protected abstract PagingAndSortingRepository<T, K> getModelRepository();

    protected abstract String getModelName();
}
