package com.tx.schoolmanagement.module.clazz.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tx.schoolmanagement.module.common.constant.DtoConstants;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public record ClazzDto(
    @JsonProperty(DtoConstants.CLAZZ_CODE)
    String code,

    @JsonProperty(DtoConstants.CLAZZ_TITLE)
    String title,

    @JsonProperty(DtoConstants.CLAZZ_DESCRIPTION)
    String description,

    @JsonProperty(DtoConstants.CREATED_DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    Date createdDate,

    @JsonProperty(DtoConstants.MODIFIED_DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    Date modifiedDate,

    @JsonProperty(DtoConstants.CLAZZ_ENABLE)
    boolean enable
) { }
