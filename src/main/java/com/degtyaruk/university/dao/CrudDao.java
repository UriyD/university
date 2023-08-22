package com.degtyaruk.university.dao;

import java.util.List;
import java.util.Optional;

public interface CrudDao<E, ID> {

    <S extends E> E add(S entity);

    void update(E entity);

    void removeById(ID id);

    Optional<E> getById(ID id);

    List<E> getAll();

    List<E> getAll(Page page);
}

