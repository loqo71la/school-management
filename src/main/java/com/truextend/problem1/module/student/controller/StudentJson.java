package com.truextend.problem1.module.student.controller;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.List;

public class StudentJson {

    public static final List<String> FIELDS = Arrays.asList("studentId", "firstName", "lastName");

    @JsonProperty("studentId")
    private Integer id;

    @JsonProperty("firstName")
    private String name;

    @JsonProperty("lastName")
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
