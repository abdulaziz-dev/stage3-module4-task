package com.mjc.school.repository.impl;

import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import org.springframework.stereotype.Repository;

@Repository
public class AuthorRepositoryImpl extends AbstractRepository<AuthorModel, Long> implements AuthorRepository {

    @Override
    protected void setFields(AuthorModel oldModel, AuthorModel newModel) {
        oldModel.setName(newModel.getName());
    }

    public AuthorModel getAuthorByNewsId(Long newsId){
        NewsModel news = entityManager.find(NewsModel.class, newsId);
        return news.getAuthor();
    }
}
