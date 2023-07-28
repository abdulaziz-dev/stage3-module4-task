package com.mjc.school.service.impl;

import com.mjc.school.repository.TagRepository;
import com.mjc.school.repository.model.TagModel;
import com.mjc.school.service.TagService;
import com.mjc.school.service.dto.TagRequestDTO;
import com.mjc.school.service.dto.TagResponseDTO;
import com.mjc.school.service.exception.ErrorCodes;
import com.mjc.school.service.exception.NotFoundException;
import com.mjc.school.service.mapper.TagModelMapper;
import com.mjc.school.service.validator.Validator;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepo;
    private final Validator validator;
    private final TagModelMapper mapper = Mappers.getMapper(TagModelMapper.class);

    @Autowired
    public TagServiceImpl(TagRepository tagRepo, Validator validator) {
        this.tagRepo = tagRepo;
        this.validator = validator;
    }

    @Override
    public List<TagResponseDTO> readAll(int page, int limit, String sortBy) {
        return mapper.modelListToDtoList(tagRepo.readAll(page, limit, sortBy));
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

    @Override
    public List<TagResponseDTO> getTagsByNewsId(Long newsId) {
        validator.validateNewsExist(newsId);
        List<TagModel> tags = tagRepo.getTagsByNewsId(newsId);
        return mapper.modelListToDtoList(tags);
    }
}
