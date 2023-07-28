package com.mjc.school.repository;

import com.mjc.school.repository.model.AuthorModel;

public interface AuthorRepository extends BaseRepository<AuthorModel, Long> {
    AuthorModel getAuthorByNewsId(Long id);
}
