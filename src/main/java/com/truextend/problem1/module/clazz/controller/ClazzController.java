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
public class ClazzController extends RestApiController<String, ClazzJson, Clazz> {

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
     * Converts to clazz a specific clazzJson.
     *
     * @param clazz to be converted.
     * @return a clazzJson.
     */
    @Override
    protected ClazzJson toJson(Clazz clazz) {
        ClazzJson clazzJson = new ClazzJson();
        clazzJson.setCode(clazz.getId());
        clazzJson.setTitle(clazz.getTitle());
        clazzJson.setDescription(clazz.getDescription());
        return clazzJson;
    }

    /**
     * Converts to clazzJson a specific clazz.
     *
     * @param clazzJson to be converted.
     * @return a clazz.
     */
    @Override
    protected Clazz toModel(ClazzJson clazzJson) {
        Clazz clazz = new Clazz();
        clazz.setId(clazzJson.getCode());
        clazz.setTitle(clazzJson.getTitle());
        clazz.setDescription(clazzJson.getDescription());
        return clazz;
    }
}
