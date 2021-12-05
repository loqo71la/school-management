package com.tx.schoolmanagement.module.common.utils;

import com.tx.schoolmanagement.module.common.service.Model;

import java.util.List;
import java.util.Objects;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

public final class ServiceUtil {

    private ServiceUtil() {
    }

    public static <K, T extends Model<K>> boolean contains(List<T> modelList, K id) {
        return indexOf(modelList, id) != -1;
    }

    public static <K, T extends Model<K>> int indexOf(List<T> modelList, K id) {
        IntFunction<K> getId = index -> modelList.get(index).getId();
        return IntStream.range(0, modelList.size())
            .filter(index -> Objects.equals(getId.apply(index), id))
            .findFirst()
            .orElse(-1);
    }
}
