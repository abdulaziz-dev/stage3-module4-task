package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.service.dto.AuthorRequestDTO;
import com.mjc.school.service.dto.AuthorResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper
public interface AuthorModelMapper {
    AuthorResponseDTO modelToDTO(AuthorModel authorModel);
    List<AuthorResponseDTO> modelListToDtoList (List<AuthorModel> newsList);
    @Mappings(value = { @Mapping(target = "createDate", ignore = true),
                        @Mapping(target = "lastUpdateDate", ignore = true)})
    AuthorModel dtoToModel (AuthorRequestDTO requestDTO);
}
