package com.mjc.school.service.impl;

import com.mjc.school.repository.CommentRepository;
import com.mjc.school.repository.NewsRepository;
import com.mjc.school.repository.model.CommentModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.CommentService;
import com.mjc.school.service.dto.CommentRequestDTO;
import com.mjc.school.service.dto.CommentResponseDTO;
import com.mjc.school.service.exception.ErrorCodes;
import com.mjc.school.service.exception.NotFoundException;
import com.mjc.school.service.mapper.CommentModelMapper;
import com.mjc.school.service.validator.Validator;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepo;
    private final NewsRepository newsRepo;
    private final CommentModelMapper mapper = Mappers.getMapper(CommentModelMapper.class);
    private final Validator validator;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, NewsRepository newsRepo, Validator validator){
        this.commentRepo = commentRepository;
        this.newsRepo = newsRepo;
        this.validator = validator;
    }

    @Override
    public List<CommentResponseDTO> readAll(int page, int limit, String sortBy) {
        return mapper.modelListToDtoList(commentRepo.readAll(page, limit, sortBy));
    }

    @Override
    public CommentResponseDTO readById(Long id) {
        return mapper.modelToDTO(commentRepo.readById(id).orElseThrow(() -> new NotFoundException(String.format(ErrorCodes.COMMENT_NOT_EXIST.getMessage(),id))));
    }

    @Override
    public CommentResponseDTO create(CommentRequestDTO createRequest) {
        validator.checkCommentDto(createRequest);
        CommentModel model = mapper.dtoToModel(createRequest);
        NewsModel news = newsRepo.readById(createRequest.newsId()).get();
        model.setNews(news);
        CommentModel newCommentModel = commentRepo.create(model);
        news.addComment(newCommentModel);
        return mapper.modelToDTO(commentRepo.create(model));
    }

    @Override
    public CommentResponseDTO update(CommentRequestDTO updateRequest) {
        validator.checkCommentDto(updateRequest);
        readById(updateRequest.id());
        CommentModel model = mapper.dtoToModel(updateRequest);
        model.setNews(newsRepo.readById(updateRequest.newsId()).get());

        CommentModel updatedModel = commentRepo.update(model);
        return mapper.modelToDTO(updatedModel);
    }

    @Override
    public boolean deleteById(Long id) {
        return commentRepo.deleteById(id);
    }

    @Override
    public List<CommentResponseDTO> getCommentsByNewsId(Long newsId) {
        validator.validateNewsExist(newsId);
        List<CommentModel> comments = commentRepo.getCommentsByNewsId(newsId);
        return mapper.modelListToDtoList(comments);
    }
}
