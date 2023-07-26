package com.mjc.school.service;

import java.util.List;

public interface BaseService<T, R, K> {
    List<R> readAll(int page, int limit, String sortBy);

    R readById(K id);

    R create(T createRequest);

    R update(T updateRequest);

    boolean deleteById(K id);
}
