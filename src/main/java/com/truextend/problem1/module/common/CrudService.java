package com.truextend.problem1.module.common;

import java.util.List;

public interface CrudService<T, K> {

    T create(T item);

    List<T> readAll();

    T read(K id);

    void update(K id, T item);

    void delete(K id);
}
