package com.truextend.problem1.module.clazz.service;

import com.truextend.problem1.module.common.service.Model;

public class Clazz implements Model<String> {

    private String id;

    private String title;

    private String description;

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

}