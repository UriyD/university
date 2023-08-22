package com.degtyaruk.university.dao.implhibernate;

import com.degtyaruk.university.dao.CrudDao;
import com.degtyaruk.university.dao.Page;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Profile("hibernate")
public abstract class AbstractCrudHibernateImpl<E> implements CrudDao<E, Integer> {

    protected static final String PART_ONE_QUERY = "SELECT ";
    protected static final String PART_TWO_QUERY = " FROM ";
    @PersistenceContext
    protected EntityManager entityManager;

    protected final Class<E> classType;

    protected AbstractCrudHibernateImpl(Class<E> classType) {
        this.classType = classType;
    }

    @Override
    @Transactional
    public <S extends E> E add(S entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    @Transactional
    public void update(E entity) {
        entityManager.merge(entity);
    }

    @Override
    @Transactional
    public void removeById(Integer id) {
        entityManager.remove(entityManager.find(classType, id));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<E> getById(Integer id) {
        return Optional.ofNullable(entityManager.find(classType, id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<E> getAll() {
        return entityManager.createQuery(buildSelectAllQuery(), classType).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<E> getAll(Page page) {
        TypedQuery<E> query = entityManager.createQuery(buildSelectAllQuery(), classType);
        query.setMaxResults(page.getLimit());
        query.setFirstResult(page.getOffset());
        return query.getResultList();
    }

    private String buildSelectAllQuery() {
        return PART_ONE_QUERY + returnAlias() + PART_TWO_QUERY + classType.getSimpleName() + " " + returnAlias();
    }

    private String returnAlias() {
        return classType.getSimpleName().substring(0, 1).toLowerCase();
    }
}

