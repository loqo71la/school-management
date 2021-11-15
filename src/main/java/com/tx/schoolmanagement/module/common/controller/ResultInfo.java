package com.tx.schoolmanagement.module.common.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public record ResultInfo(

    @JsonProperty("status")
    ResultStatus status,

    @JsonProperty("message")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String message
) { }
