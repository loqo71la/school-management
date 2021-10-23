package com.tx.schoolmanagement.module.clazz.service;

import com.tx.schoolmanagement.module.common.constant.DtoFieldConstants;
import com.tx.schoolmanagement.module.common.service.VolatileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import static java.util.Comparator.comparing;

@Service
public class ClazzVolatileService extends VolatileService<String, Clazz> {

    /**
     * Stores the error message for unauthorized.
     */
    private static final String UNAUTHORIZED_ERROR = "Class already exists";

    /**
     * Stores the error message for not found.
     */
    private static final String NOT_FOUND_ERROR = "Class not found";

    /**
     * Stores the class code template.
     */
    private static final String CLAZZ_CODE_TEMPLATE = "%s-%03d";

    /**
     * Stores the default class code
     */
    private static final String DEFAULT_CLAZZ_CODE = "0A-000";

    /**
     * Stores the value of number THREE.
     */
    private static final int THREE = 3;

    /**
     * Default Constructor to load properties.
     */
    public ClazzVolatileService() {
        super.unauthorizedErrorMessage = UNAUTHORIZED_ERROR;
        super.notFoundErrorMessage = NOT_FOUND_ERROR;
    }

    /**
     * Auto injects the volatile classes in the system.
     *
     * @param volatileClazzes to be set.
     */
    @Autowired
    public void setVolatileStudents(Map<String, Clazz> volatileClazzes) {
        super.volatileData = volatileClazzes;
    }

    @Override
    public boolean match(Clazz clazz, Map<String, String> queryParams) {
        boolean isMatchedTitle = Boolean.FALSE;
        if (queryParams.containsKey(DtoFieldConstants.CLAZZ_TITLE)) {
            isMatchedTitle = clazz.getTitle()
                    .toLowerCase()
                    .contains(queryParams.get(DtoFieldConstants.CLAZZ_TITLE));
        }

        boolean isMatchedDescription = Boolean.FALSE;
        if (queryParams.containsKey(DtoFieldConstants.CLAZZ_DESCRIPTION)) {
            isMatchedDescription = clazz.getDescription()
                    .toLowerCase()
                    .contains(queryParams.get(DtoFieldConstants.CLAZZ_DESCRIPTION));
        }
        return isMatchedTitle || isMatchedDescription;
    }

    @Override
    protected String findLastId() {
        String lastId = super.volatileData
                .keySet()
                .stream()
                .max(comparing(id -> id))
                .orElse(DEFAULT_CLAZZ_CODE);
        int room = Integer.parseInt(lastId.substring(THREE));
        return String.format(CLAZZ_CODE_TEMPLATE, lastId.substring(0, THREE - 1), ++room);
    }
}
