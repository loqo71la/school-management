package com.tx.schoolmanagement.module.student.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tx.schoolmanagement.module.common.constant.DtoConstants;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public record StudentDto(

    @JsonProperty(DtoConstants.STUDENT_ID)
    String id,

    @JsonProperty(DtoConstants.STUDENT_NAME)
    String name,

    @JsonProperty(DtoConstants.STUDENT_LAST_NAME)
    String lastname,

    @JsonProperty(DtoConstants.CREATED_DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    Date createdDate,

    @JsonProperty(DtoConstants.MODIFIED_DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    Date modifiedDate,

    @JsonProperty(DtoConstants.STUDENT_GENDER)
    boolean gender
) { }
