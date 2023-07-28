package com.mjc.school.service;

import com.mjc.school.service.dto.CommentRequestDTO;
import com.mjc.school.service.dto.CommentResponseDTO;

import java.util.List;

public interface CommentService extends BaseService<CommentRequestDTO, CommentResponseDTO, Long> {
    List<CommentResponseDTO> getCommentsByNewsId(Long newsId);
}
