package com.tx.schoolmanagement.module.clazz.mapper;

import com.tx.schoolmanagement.module.clazz.controller.ClazzDto;
import com.tx.schoolmanagement.module.clazz.repository.Clazz;
import com.tx.schoolmanagement.module.common.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class ClazzMapper implements Mapper<ClazzDto, Clazz> {

    /**
     * Converts clazz to clazzDto.
     *
     * @param clazz to be converted.
     * @return a clazzDto.
     */
    @Override
    public ClazzDto toDto(Clazz clazz) {
        return new ClazzDto(
            clazz.getId(),
            clazz.getTitle(),
            clazz.getDescription(),
            clazz.getCreatedDate(),
            clazz.getModifiedDate(),
            clazz.getEnable()
        );
    }

    /**
     * Converts clazzDto to clazz.
     *
     * @param clazzDto to be converted.
     * @return a clazz.
     */
    @Override
    public Clazz toModel(ClazzDto clazzDto) {
        Clazz clazz = new Clazz();
        clazz.setId(clazzDto.code());
        clazz.setTitle(clazzDto.title());
        clazz.setDescription(clazzDto.description());
        clazz.setEnable(clazzDto.enable());
        return clazz;
    }
}
