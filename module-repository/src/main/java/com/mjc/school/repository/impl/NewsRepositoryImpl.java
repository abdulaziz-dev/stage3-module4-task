package com.mjc.school.repository.impl;

import com.mjc.school.repository.NewsRepository;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.model.TagModel;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@Repository
public class NewsRepositoryImpl extends AbstractRepository<NewsModel, Long> implements NewsRepository {


    @Override
    protected void setFields(NewsModel oldModel, NewsModel newModel) {
        oldModel.setTitle(newModel.getTitle());
        oldModel.setContent(newModel.getContent());
        oldModel.setAuthor(newModel.getAuthor());
        oldModel.setTags(newModel.getTags());
    }


    public List<NewsModel> readByParams(Long tagId, String tagName, String authorName, String title, String content){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<NewsModel> cr = cb.createQuery(NewsModel.class);
        Root<NewsModel> root = cr.from(NewsModel.class);

        Join<NewsModel, TagModel> tags = root.join("tags");
        Join<NewsModel, AuthorModel> author = root.join("author");

        if (tagId != null){
            Predicate tagIdCheck = cb.equal(tags.get("id"), tagId);
            cr.select(root).where(tagIdCheck);
        }
        if (tagName != null && !tagName.isBlank()){
            Predicate tagNameCheck = cb.equal(tags.get("name"), tagName);
            cr.select(root).where(tagNameCheck);
        }
        if (authorName != null && !authorName.isBlank()){
            Predicate authorCheck = cb.equal(author.get("name"), authorName);
            cr.select(root).where(authorCheck);
        }
        if (title != null && !title.isBlank()){
            Predicate titleCheck  = cb.equal(root.get("title"), title);
            cr.select(root).where(titleCheck);
        }
        if (content != null && !content.isBlank()){
            Predicate contentCheck  = cb.equal(root.get("content"), content);
            cr.select(root).where(contentCheck);
        }

        TypedQuery<NewsModel> query = entityManager.createQuery(cr);
        return query.getResultList();
    }

}
