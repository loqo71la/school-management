package com.truextend.problem1.module.clazz.controller;

import com.truextend.problem1.module.clazz.service.Clazz;
import com.truextend.problem1.module.clazz.service.ClazzVolatileService;
import com.truextend.problem1.module.common.constant.ControllerConstants;
import com.truextend.problem1.module.common.constant.JsonFieldConstants;
import com.truextend.problem1.module.common.controller.RestApiController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * Manages the http request for classes.
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
     * Returns the list of ClazzJson field names.
     *
     * @return the list of fields.
     */
    @Override
    protected List<String> getJsonFields() {
        return Arrays.asList(JsonFieldConstants.CLAZZ_CODE,
                JsonFieldConstants.CLAZZ_TITLE,
                JsonFieldConstants.CLAZZ_DESCRIPTION);
    }

    /**
     * Converts clazz to clazzDto.
     *
     * @param clazz to be converted.
     * @return a clazzDto.
     */
    @Override
    protected ClazzDto toDto(Clazz clazz) {
        ClazzDto clazzDto = new ClazzDto();
        clazzDto.setCode(clazz.getId());
        clazzDto.setTitle(clazz.getTitle());
        clazzDto.setDescription(clazz.getDescription());
        return clazzDto;
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
        clazz.setId(clazzDto.getCode());
        clazz.setTitle(clazzDto.getTitle());
        clazz.setDescription(clazzDto.getDescription());
        return clazz;
    }
}
