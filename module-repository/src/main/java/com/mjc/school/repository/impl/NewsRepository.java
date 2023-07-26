package com.mjc.school.repository.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.model.TagModel;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class NewsRepository implements BaseRepository<NewsModel, Long> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<NewsModel> readAll() {
        return entityManager.createQuery("select n from NewsModel n", NewsModel.class).getResultList();
    }

    @Override
    public Optional<NewsModel> readById(Long id) {
        return Optional.ofNullable(entityManager.find(NewsModel.class, id));
    }

    @Override
    public NewsModel create(NewsModel entity) {
        entityManager.persist(entity);
        entityManager.flush();
        return entity;
    }

    @Override
    public NewsModel update(NewsModel entity) {
        NewsModel model = entityManager.find(NewsModel.class, entity.getId());
        model.setTitle(entity.getTitle());
        model.setContent(entity.getContent());
        model.setLastUpdateDate(entity.getLastUpdateDate());
        model.setAuthor(entity.getAuthor());
        model.setTags(entity.getTags());
        entityManager.flush();
        return model;
    }

    @Override
    public boolean deleteById(Long id) {
        if (existById(id)){
            entityManager.createQuery("delete from NewsModel n where n.id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            return true;
        }
        return false;
    }

    @Override
    public boolean existById(Long id) {
        return entityManager.find(NewsModel.class, id) != null;
    }

    public List<NewsModel> readByParams(Long tagId, String tagName, String authorName, String title, String content){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<NewsModel> cr = cb.createQuery(NewsModel.class);
        Root<NewsModel> root = cr.from(NewsModel.class);

        Join<NewsModel, TagModel> tags = root.join("tags");
        Join<NewsModel, AuthorModel> author = root.join("author");

        Predicate tagIdCheck = cb.equal(tags.get("id"), tagId);
        Predicate tagNameCheck = cb.equal(tags.get("name"), tagName);
        Predicate authorCheck = cb.equal(author.get("name"), authorName);
        Predicate titleCheck  = cb.equal(root.get("title"), title);
        Predicate contentCheck  = cb.equal(root.get("content"), content);

        if (tagId != null) cr.select(root).where(tagIdCheck);
        if (!tagName.isBlank()) cr.select(root).where(tagNameCheck);
        if (!authorName.isBlank()) cr.select(root).where(authorCheck);
        if (!title.isBlank()) cr.select(root).where(titleCheck);
        if (!content.isBlank()) cr.select(root).where(contentCheck);

        TypedQuery<NewsModel> query = entityManager.createQuery(cr);
        return query.getResultList();
    }
}
