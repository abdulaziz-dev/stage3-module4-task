package com.mjc.school.service;

import com.mjc.school.service.dto.AuthorRequestDTO;
import com.mjc.school.service.dto.AuthorResponseDTO;

public interface AuthorService extends BaseService<AuthorRequestDTO, AuthorResponseDTO, Long> {
    AuthorResponseDTO getAuthorByNewsId(Long newsId);
}
