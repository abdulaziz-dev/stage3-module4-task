package com.mjc.school.service;

import com.mjc.school.service.dto.NewsRequestDTO;
import com.mjc.school.service.dto.NewsResponseDTO;

import java.util.List;

public interface NewsService extends BaseService<NewsRequestDTO, NewsResponseDTO, Long>{
    List<NewsResponseDTO> readByParams(Long tagId, String tagName, String authorName, String title, String content);
}
