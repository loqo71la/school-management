package com.tx.schoolmanagement.module.student.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tx.schoolmanagement.module.common.constant.DtoFieldConstants;

public record StudentDto(

    @JsonProperty(DtoFieldConstants.STUDENT_ID)
    Integer id,

    @JsonProperty(DtoFieldConstants.STUDENT_NAME)
    String name,

    @JsonProperty(DtoFieldConstants.STUDENT_LAST_NAME)
    String lastName
) { }
