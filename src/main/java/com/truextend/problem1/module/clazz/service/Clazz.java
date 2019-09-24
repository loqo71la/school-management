package com.truextend.problem1.module.clazz.service;

import com.truextend.problem1.module.common.service.Model;

import java.util.ArrayList;
import java.util.List;

public class Clazz implements Model<String> {

    private String id;

    private String title;

    private String description;

    private final List<Integer> studentIdList;

    public Clazz() {
        studentIdList = new ArrayList<>();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
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

    public List<Integer> getStudentIdList() {
        return studentIdList;
    }

    public void addStudentId(Integer studentId) {
        studentIdList.add(studentId);
    }

    @Override
    public int compareTo(Model<String> clazz) {
        return id.compareTo(clazz.getId());
    }
}
