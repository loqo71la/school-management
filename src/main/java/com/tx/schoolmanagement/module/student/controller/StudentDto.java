package com.tx.schoolmanagement.module.student.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tx.schoolmanagement.module.common.constant.DtoFieldConstants;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public record StudentDto(

    @JsonProperty(DtoFieldConstants.STUDENT_ID)
    String id,

    @JsonProperty(DtoFieldConstants.STUDENT_NAME)
    String name,

    @JsonProperty(DtoFieldConstants.STUDENT_LAST_NAME)
    String lastname,

    @JsonProperty(DtoFieldConstants.CREATED_DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    Date createdDate,

    @JsonProperty(DtoFieldConstants.MODIFIED_DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    Date modifiedDate,

    @JsonProperty(DtoFieldConstants.STUDENT_GENDER)
    boolean gender
) { }
