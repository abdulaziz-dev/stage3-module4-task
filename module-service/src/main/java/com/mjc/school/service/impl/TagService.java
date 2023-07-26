package com.mjc.school.service.impl;

import com.mjc.school.repository.impl.TagRepository;
import com.mjc.school.repository.model.TagModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.TagRequestDTO;
import com.mjc.school.service.dto.TagResponseDTO;
import com.mjc.school.service.exception.ErrorCodes;
import com.mjc.school.service.exception.NotFoundException;
import com.mjc.school.service.mapper.TagModelMapper;
import com.mjc.school.service.validator.Validator;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService implements BaseService<TagRequestDTO, TagResponseDTO, Long> {

    private final TagRepository tagRepo;
    private final Validator validator;
    private final TagModelMapper mapper = Mappers.getMapper(TagModelMapper.class);
    @Autowired
    public TagService(TagRepository tagRepo, Validator validator) {
        this.tagRepo = tagRepo;
        this.validator = validator;
    }

    @Override
    public List<TagResponseDTO> readAll(int page, int limit, String sortBy) {

        return mapper.modelListToDtoList(tagRepo.readAll());
    }

    @Override
    public TagResponseDTO readById(Long id) {
        TagModel model = tagRepo.readById(id).orElseThrow(() -> new NotFoundException(String.format(ErrorCodes.TAG_NOT_EXIST.getMessage(), id)));
        return mapper.modelToDTO(model);
    }

    @Override
    public TagResponseDTO create(TagRequestDTO createRequest) {
        validator.checkTagDto(createRequest);
        TagModel model = mapper.dtoToModel(createRequest);
        tagRepo.create(model);
        return mapper.modelToDTO(model);
    }

    @Override
    public TagResponseDTO update(TagRequestDTO updateRequest) {
        TagModel model = mapper.dtoToModel(updateRequest);
        checkExist(model.getId());
        validator.checkTagDto(updateRequest);
        TagModel updatedModel = tagRepo.update(model);
        return mapper.modelToDTO(updatedModel);
    }

    @Override
    public boolean deleteById(Long id) {
        checkExist(id);
        return tagRepo.deleteById(id);
    }

    private void checkExist(Long id){
        if (!tagRepo.existById(id)){
            throw new NotFoundException(String.format(ErrorCodes.TAG_NOT_EXIST.getMessage(), id));
        }
    }
}
