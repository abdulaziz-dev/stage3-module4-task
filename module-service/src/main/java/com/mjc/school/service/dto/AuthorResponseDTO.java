package com.mjc.school.service.dto;

import java.time.LocalDateTime;

public record AuthorResponseDTO(Long id, String name, LocalDateTime createDate, LocalDateTime lastUpdateDate) {
}
