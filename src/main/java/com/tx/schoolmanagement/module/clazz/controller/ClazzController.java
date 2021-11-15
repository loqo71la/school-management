package com.tx.schoolmanagement.module.clazz.controller;

import com.tx.schoolmanagement.module.clazz.mapper.ClazzMapper;
import com.tx.schoolmanagement.module.clazz.repository.Clazz;
import com.tx.schoolmanagement.module.clazz.service.ClazzService;
import com.tx.schoolmanagement.module.common.constant.RouteConstants;
import com.tx.schoolmanagement.module.common.controller.RestApiController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.tx.schoolmanagement.module.common.constant.DtoConstants.CLAZZ_MODEL;

/**
 * Manages the http request for clazzes.
 */
@RestController
@RequestMapping(RouteConstants.CLAZZ_URL)
public class ClazzController extends RestApiController<String, ClazzDto, Clazz> {

    /**
     * Auto-injects a proper service.
     *
     * @param clazzService CrudService implementation for classes.
     */
    @Autowired
    public void setService(ClazzService clazzService) {
        super.modelService = clazzService;
    }

    /**
     * Auto-injects a proper mapper.
     *
     * @param clazzMapper ClazzMapper implementation.
     */
    @Autowired
    public void setMapper(ClazzMapper clazzMapper) {
        super.mapper = clazzMapper;
    }

    @Override
    protected String getModelName() {
        return CLAZZ_MODEL;
    }
}
