package com.truextend.problem1.module.common.service;

public interface Model<K extends Comparable> {
    K getId();

    void setId(K id);
}
