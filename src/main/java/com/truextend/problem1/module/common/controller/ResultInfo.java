package com.truextend.problem1.module.common.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ResultInfo {

    @JsonProperty("status")
    private ResultStatus status;

    @JsonProperty("message")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    public ResultStatus getStatus() {
        return status;
    }

    public void setStatus(ResultStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
