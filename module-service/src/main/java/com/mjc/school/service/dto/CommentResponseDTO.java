package com.mjc.school.service.dto;

import java.time.LocalDateTime;

public record CommentResponseDTO(Long id, String content, LocalDateTime createDate,
                                 LocalDateTime lastUpdateDate, Long newsId) {
}
