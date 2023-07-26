package com.mjc.school.service.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.CommentModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.CommentRequestDTO;
import com.mjc.school.service.dto.CommentResponseDTO;
import com.mjc.school.service.exception.ErrorCodes;
import com.mjc.school.service.exception.NotFoundException;
import com.mjc.school.service.mapper.CommentModelMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CommentService implements BaseService<CommentRequestDTO, CommentResponseDTO, Long> {

    private final BaseRepository<CommentModel, Long> repository;
    private final CommentModelMapper mapper = Mappers.getMapper(CommentModelMapper.class);

    @Autowired
    public CommentService(BaseRepository<CommentModel, Long> commentRepository){
        this.repository = commentRepository;
    }

    @Override
    public List<CommentResponseDTO> readAll(int page, int limit, String sortBy) {
        return mapper.modelListToDtoList(repository.readAll());
    }

    @Override
    public CommentResponseDTO readById(Long id) {
        return mapper.modelToDTO(repository.readById(id).orElseThrow(() -> new NotFoundException(String.format(ErrorCodes.COMMENT_NOT_EXIST.getMessage(),id))));
    }

    @Override
    public CommentResponseDTO create(CommentRequestDTO createRequest) {
        //  validation //
        CommentModel model = mapper.dtoToModel(createRequest);
        return mapper.modelToDTO(repository.create(model));
    }

    @Override
    public CommentResponseDTO update(CommentRequestDTO updateRequest) {
        // validation //
        CommentModel model = mapper.dtoToModel(updateRequest);
        CommentModel updatedModel = repository.update(model);
        return mapper.modelToDTO(updatedModel);
    }

    @Override
    public boolean deleteById(Long id) {
        return repository.deleteById(id);
    }
}
