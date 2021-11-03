package com.tx.schoolmanagement.module.clazz.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tx.schoolmanagement.module.common.constant.DtoFieldConstants;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public record ClazzDto(
    @JsonProperty(DtoFieldConstants.CLAZZ_CODE)
    String code,

    @JsonProperty(DtoFieldConstants.CLAZZ_TITLE)
    String title,

    @JsonProperty(DtoFieldConstants.CLAZZ_DESCRIPTION)
    String description,

    @JsonProperty(DtoFieldConstants.CREATED_DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    Date createdDate,

    @JsonProperty(DtoFieldConstants.MODIFIED_DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    Date modifiedDate,

    @JsonProperty(DtoFieldConstants.CLAZZ_ENABLE)
    boolean enable
) { }
