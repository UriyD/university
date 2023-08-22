package com.degtyaruk.university.service;

import com.degtyaruk.university.dao.Page;

import java.util.List;
import java.util.Optional;

public interface UniversityService<T, ID> {
    T add(T entity);

    void update(T entity);

    void removeById(ID id);

    T getById(ID id);

    List<T> getAll();

    List<T> getAll(Page page);
}

