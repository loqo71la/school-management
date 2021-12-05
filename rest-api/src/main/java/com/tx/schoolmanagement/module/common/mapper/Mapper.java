package com.tx.schoolmanagement.module.common.mapper;

public interface Mapper<T, U> {

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
