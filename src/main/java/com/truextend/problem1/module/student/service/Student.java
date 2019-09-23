package com.truextend.problem1.module.student.service;

import com.truextend.problem1.module.common.service.Model;

public class Student implements Model<Integer> {

    private Integer id;

    private String name;

    private String lastName;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
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
