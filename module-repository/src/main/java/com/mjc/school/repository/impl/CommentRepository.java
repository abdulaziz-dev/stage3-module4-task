package com.mjc.school.repository.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.CommentModel;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class CommentRepository implements BaseRepository<CommentModel, Long> {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<CommentModel> readAll() {
        return manager.createQuery("select c from CommentModel c", CommentModel.class).getResultList();
    }

    @Override
    public Optional<CommentModel> readById(Long id) {
        return Optional.ofNullable(manager.find(CommentModel.class, id));
    }

    @Override
    public CommentModel create(CommentModel entity) {
        manager.persist(entity);
        manager.flush();
        return entity;
    }

    @Override
    public CommentModel update(CommentModel entity) {
        CommentModel model = readById(entity.getId()).orElse(null);
        model.setContent(entity.getContent());
        return model;
    }

    @Override
    public boolean deleteById(Long id) {
        if (existById(id)){
            manager.remove(readById(id));
            return true;
        }
        return false;
    }

    @Override
    public boolean existById(Long id) {
        return manager.find(CommentModel.class, id) != null;
    }
}
