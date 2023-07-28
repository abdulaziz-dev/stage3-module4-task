package com.mjc.school.service;

import com.mjc.school.service.dto.TagRequestDTO;
import com.mjc.school.service.dto.TagResponseDTO;

import java.util.List;

public interface TagService extends BaseService<TagRequestDTO, TagResponseDTO, Long>{
    List<TagResponseDTO> getTagsByNewsId(Long newsId);
}
