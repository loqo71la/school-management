package com.truextend.problem1.module.student.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.truextend.problem1.module.common.constant.JsonFieldConstants;

public class StudentDto {

    @JsonProperty(JsonFieldConstants.STUDENT_ID)
    private Integer id;

    @JsonProperty(JsonFieldConstants.STUDENT_NAME)
    private String name;

    @JsonProperty(JsonFieldConstants.STUDENT_LAST_NAME)
    private String lastName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
