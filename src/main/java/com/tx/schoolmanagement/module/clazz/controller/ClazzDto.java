package com.tx.schoolmanagement.module.clazz.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tx.schoolmanagement.module.common.constant.DtoFieldConstants;

public record ClazzDto(
    @JsonProperty(DtoFieldConstants.CLAZZ_CODE)
    String code,

    @JsonProperty(DtoFieldConstants.CLAZZ_TITLE)
    String title,

    @JsonProperty(DtoFieldConstants.CLAZZ_DESCRIPTION)
    String description
) { }
