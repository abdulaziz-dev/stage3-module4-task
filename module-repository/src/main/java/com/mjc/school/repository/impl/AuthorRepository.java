package com.mjc.school.repository.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.AuthorModel;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class AuthorRepository implements BaseRepository<AuthorModel, Long> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<AuthorModel> readAll() {
        return entityManager.createQuery("select a from AuthorModel a", AuthorModel.class).getResultList();
    }

    @Override
    public Optional<AuthorModel> readById(Long id) {
        return Optional.ofNullable(entityManager.find(AuthorModel.class, id));
    }

    @Override
    public AuthorModel create(AuthorModel entity) {
        entityManager.persist(entity);
        entityManager.flush();
        return entity;
    }

    @Override
    public AuthorModel update(AuthorModel entity) {
        AuthorModel model = entityManager.find(AuthorModel.class, entity.getId());
        model.setLastUpdateDate(entity.getLastUpdateDate());
        model.setName(entity.getName());
        entityManager.flush();
        return model;
    }


    @Override
    public boolean deleteById(Long id) {
        entityManager.remove(entityManager.find(AuthorModel.class, id));
        return true;
    }

    @Override
    public boolean existById(Long id) {
        Object o = entityManager.createQuery("select a from AuthorModel a where a.id = :id")
                .setParameter("id", id)
                .getSingleResult();
        return o != null;
    }
}
