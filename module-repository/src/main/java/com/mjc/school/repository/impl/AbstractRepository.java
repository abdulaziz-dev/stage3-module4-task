package com.mjc.school.repository.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.BaseEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

public abstract class AbstractRepository<T extends BaseEntity<K>, K> implements BaseRepository<T, K> {
    @PersistenceContext
    protected EntityManager entityManager;

    private final Class<T> entityClass;

    public AbstractRepository(){
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }

    protected abstract void setFields(T oldModel, T newModel);

    @Override
    public List<T> readAll(int page, int limit, String sortBy) {
        int offset = page * limit;
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cr = cb.createQuery(entityClass);
        Root<T> root = cr.from(entityClass);
        CriteriaQuery<T> select = cr.select(root);

        CriteriaQuery<T> ordered = select.orderBy(cb.asc(root.get(sortBy)));
        TypedQuery<T> query = entityManager.createQuery(ordered)
                .setFirstResult(offset)
                .setMaxResults(limit);
        return query.getResultList();
    }

    @Override
    public Optional<T> readById(K id) {
        return Optional.ofNullable(entityManager.find(entityClass, id));
    }

    @Override
    public T create(T entity) {
        entityManager.persist(entity);
        entityManager.flush();
        return entity;
    }

    @Override
    public T update(T entity) {
        T model = entityManager.find(entityClass, entity.getId());
        setFields(model, entity);
        entityManager.flush();
        return model;
    }

    @Override
    public boolean deleteById(K id) {
        T tag = entityManager.find(entityClass, id);
        if (tag == null){
            return false;
        }
        entityManager.remove(tag);
        entityManager.flush();
        return true;
    }

    @Override
    public boolean existById(K id) {
        return entityManager.find(entityClass, id) != null;
    }

}
