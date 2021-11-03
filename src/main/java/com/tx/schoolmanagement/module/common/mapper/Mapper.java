package com.tx.schoolmanagement.module.common.mapper;

import java.util.List;

public interface Mapper<T, U> {

    /**
     * Returns the list of dto fields.
     *
     * @return the list of fields.
     */
    List<String> getDtoFields();

    /**
     * Converts model to dto.
     *
     * @param model to be converted.
     * @return a dto.
     */
    T toDto(U model);

    /**
     * Converts dto to model.
     *
     * @param dto to be converted.
     * @return a model.
     */
    U toModel(T dto);
}
