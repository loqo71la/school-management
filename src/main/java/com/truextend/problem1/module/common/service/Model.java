package com.truextend.problem1.module.common.service;

public interface Model<K> extends Comparable<Model<K>> {

    K getId();

    void setId(K id);
}
