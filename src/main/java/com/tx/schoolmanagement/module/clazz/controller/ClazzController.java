package com.tx.schoolmanagement.module.clazz.controller;

import com.tx.schoolmanagement.module.clazz.service.Clazz;
import com.tx.schoolmanagement.module.clazz.service.ClazzVolatileService;
import com.tx.schoolmanagement.module.common.constant.ControllerConstants;
import com.tx.schoolmanagement.module.common.constant.DtoFieldConstants;
import com.tx.schoolmanagement.module.common.controller.RestApiController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * Manages the http request for clazzes.
 */
@RestController
@RequestMapping(ControllerConstants.CLAZZ_URL)
public class ClazzController extends RestApiController<String, ClazzDto, Clazz> {

    /**
     * Auto injects a proper service.
     *
     * @param clazzService CrudService implementation for classes.
     */
    @Autowired
    public void setService(ClazzVolatileService clazzService) {
        super.modelService = clazzService;
    }

    /**
     * Returns the list of ClazzDto field names.
     *
     * @return the list of fields.
     */
    @Override
    protected List<String> getDtoFields() {
        return Arrays.asList(DtoFieldConstants.CLAZZ_CODE,
                DtoFieldConstants.CLAZZ_TITLE,
                DtoFieldConstants.CLAZZ_DESCRIPTION);
    }

    /**
     * Converts clazz to clazzDto.
     *
     * @param clazz to be converted.
     * @return a clazzDto.
     */
    @Override
    protected ClazzDto toDto(Clazz clazz) {
        return new ClazzDto(
            clazz.getId(),
            clazz.getTitle(),
            clazz.getDescription()
        );
    }

    /**
     * Converts clazzDto to clazz.
     *
     * @param clazzDto to be converted.
     * @return a clazz.
     */
    @Override
    protected Clazz toModel(ClazzDto clazzDto) {
        Clazz clazz = new Clazz();
        clazz.setId(clazzDto.code());
        clazz.setTitle(clazzDto.title());
        clazz.setDescription(clazzDto.description());
        return clazz;
    }
}
