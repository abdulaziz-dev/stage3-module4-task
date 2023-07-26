package com.mjc.school.service.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.AuthorRequestDTO;
import com.mjc.school.service.dto.AuthorResponseDTO;
import com.mjc.school.service.exception.ErrorCodes;
import com.mjc.school.service.exception.NotFoundException;
import com.mjc.school.service.mapper.AuthorModelMapper;
import com.mjc.school.service.validator.Validator;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuthorService implements BaseService<AuthorRequestDTO, AuthorResponseDTO, Long> {
    private final BaseRepository<AuthorModel, Long> authorRepository;
    private final Validator validator;
    private final AuthorModelMapper mapper = Mappers.getMapper(AuthorModelMapper.class);

    @Autowired
    public AuthorService(BaseRepository<AuthorModel, Long> authorRepository, Validator validator) {
        this.authorRepository = authorRepository;
        this.validator = validator;
    }

    @Override
    public List<AuthorResponseDTO> readAll(int page, int limit, String sortBy) {
        return mapper.modelListToDtoList(authorRepository.readAll());
    }

    @Override
    public AuthorResponseDTO readById(Long id) {
        AuthorModel dto = authorRepository.readById(id).orElseThrow(() -> new NotFoundException(String.format(ErrorCodes.AUTHOR_NOT_EXIST.getMessage(), id)));
        return mapper.modelToDTO(dto);
    }

    @Override
    public AuthorResponseDTO create(AuthorRequestDTO createRequest) {
        validator.checkAuthorDto(createRequest);
        AuthorModel model = mapper.dtoToModel(createRequest);
        AuthorModel newModel = authorRepository.create(model);
        return mapper.modelToDTO(newModel);
    }

    @Override
    public AuthorResponseDTO update(AuthorRequestDTO updateRequest) {
        validator.checkAuthorDto(updateRequest);
        AuthorModel model = mapper.dtoToModel(updateRequest);
        readById(model.getId());
        AuthorModel updatedModel = authorRepository.update(model);
        return mapper.modelToDTO(updatedModel);
    }

    @Override
    public boolean deleteById(Long id) {
        if (authorRepository.existById(id)){
            return authorRepository.deleteById(id);
        } else throw new NotFoundException(String.format(ErrorCodes.AUTHOR_NOT_EXIST.getMessage(), id));
    }

}
