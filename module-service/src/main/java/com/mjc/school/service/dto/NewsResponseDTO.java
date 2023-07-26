package com.mjc.school.service.dto;

import com.mjc.school.repository.model.TagModel;

import java.time.LocalDateTime;
import java.util.Set;

public record NewsResponseDTO(Long id, String title, String content,
                              LocalDateTime createDate, LocalDateTime lastUpdateDate,
                              Long authorId, Set<TagResponseDTO> tagsSet) {
}