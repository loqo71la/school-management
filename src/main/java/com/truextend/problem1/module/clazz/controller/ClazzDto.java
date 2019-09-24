package com.truextend.problem1.module.clazz.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.truextend.problem1.module.common.constant.JsonFieldConstants;

public class ClazzDto {

    @JsonProperty(JsonFieldConstants.CLAZZ_CODE)
    private String code;

    @JsonProperty(JsonFieldConstants.CLAZZ_TITLE)
    private String title;

    @JsonProperty(JsonFieldConstants.CLAZZ_DESCRIPTION)
    private String description;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
