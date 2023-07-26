package com.mjc.school.repository.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.TagModel;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class TagRepository implements BaseRepository<TagModel, Long> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<TagModel> readAll() {
        return entityManager.createQuery("select t from TagModel t", TagModel.class).getResultList();
    }

    @Override
    public Optional<TagModel> readById(Long id) {
        return Optional.ofNullable(entityManager.find(TagModel.class, id));
    }

    @Override
    public TagModel create(TagModel entity) {
        entityManager.persist(entity);
        entityManager.flush();
        return entity;
    }

    @Override
    public TagModel update(TagModel entity) {
        TagModel model = entityManager.find(TagModel.class, entity.getId());
        model.setName(entity.getName());
        entityManager.flush();
        return model;
    }

    @Override
    public boolean deleteById(Long id) {
        TagModel tag = entityManager.find(TagModel.class, id);
        if (tag == null){
            return false;
        }
        entityManager.remove(tag);
        entityManager.flush();
        return true;
    }

    @Override
    public boolean existById(Long id) {
        return entityManager.find(TagModel.class, id) != null;
    }


}
