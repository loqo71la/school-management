package com.tx.schoolmanagement.module.common.service;

public interface Model<K> extends Comparable<Model<K>> {

    K getId();

    void setId(K id);
}
