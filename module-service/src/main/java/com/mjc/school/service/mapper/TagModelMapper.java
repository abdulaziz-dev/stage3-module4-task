package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.TagModel;
import com.mjc.school.service.dto.TagRequestDTO;
import com.mjc.school.service.dto.TagResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface TagModelMapper {

    TagResponseDTO modelToDTO(TagModel model);

    List<TagResponseDTO> modelListToDtoList(List<TagModel> modelList);

    @Mapping(target = "news", ignore = true)
    TagModel dtoToModel(TagRequestDTO dto);

}
