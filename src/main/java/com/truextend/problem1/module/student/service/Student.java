package com.truextend.problem1.module.student.service;

import com.truextend.problem1.module.common.service.Model;

import java.util.ArrayList;
import java.util.List;

public class Student implements Model<Integer> {

    private Integer id;

    private String name;

    private String lastName;

    private List<String> clazzCodeList;

    public Student() {
        clazzCodeList = new ArrayList<>();
    }

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

    public List<String> getClazzCodeList() {
        return clazzCodeList;
    }

    public void addClazzCode(String clazzCode) {
        clazzCodeList.add(clazzCode);
    }

    @Override
    public int compareTo(Model<Integer> student) {
        return id.compareTo(student.getId());
    }
}
