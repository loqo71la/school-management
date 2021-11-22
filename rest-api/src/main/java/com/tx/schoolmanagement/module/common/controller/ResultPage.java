package com.tx.schoolmanagement.module.common.controller;

import java.util.List;

public record ResultPage<T>(
    int totalItem,
    int totalPage,
    int currentPage,
    List<T> items
) { }
