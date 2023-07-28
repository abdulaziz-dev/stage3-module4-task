package com.mjc.school.service.validator;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.dto.AuthorRequestDTO;
import com.mjc.school.service.dto.CommentRequestDTO;
import com.mjc.school.service.dto.NewsRequestDTO;
import com.mjc.school.service.dto.TagRequestDTO;
import com.mjc.school.service.exception.ErrorCodes;
import com.mjc.school.service.exception.NotFoundException;
import com.mjc.school.service.exception.ValidatorException;
import org.springframework.stereotype.Component;

@Component
public class Validator {

    private final BaseRepository<AuthorModel, Long> authorRepo;
    private final BaseRepository<NewsModel, Long> newsRepo;

    private static final int TITLE_MAX_LENGTH = 30;
    private static final int TITLE_MIN_LENGTH = 5;
    private static final int CONTENT_MIN_LENGTH = 5;
    private static final int CONTENT_MAX_LENGTH = 255;
    private static final int AUTHOR_NAME_MIN_LENGTH = 3;
    private static final int AUTHOR_NAME_MAX_LENGTH = 15;
    private static final int TAG_MIN_LENGTH = 3;
    private static final int TAG_MAX_LENGTH = 15;

    public Validator(BaseRepository<AuthorModel, Long> authorRepo, BaseRepository<NewsModel, Long> newsRepo) {
        this.authorRepo = authorRepo;
        this.newsRepo = newsRepo;
    }

    public void checkNewsDto(NewsRequestDTO dto){

        validateLength(dto.title(), TITLE_MIN_LENGTH, TITLE_MAX_LENGTH, "CHECK_TITLE_LENGTH");
        validateLength(dto.content(), CONTENT_MIN_LENGTH, CONTENT_MAX_LENGTH, "CHECK_CONTENT_LENGTH");
        long authorId = dto.authorId();
        if (!authorRepo.existById(authorId)){
            throw new NotFoundException(String.format(ErrorCodes.AUTHOR_NOT_EXIST.getMessage(),authorId));
        }
    }

    public void checkAuthorDto(AuthorRequestDTO dto){
        validateLength(dto.name(), AUTHOR_NAME_MIN_LENGTH, AUTHOR_NAME_MAX_LENGTH, "CHECK_AUTHOR_NAME_LENGTH");
    }

    public void checkTagDto(TagRequestDTO dto){
        validateLength(dto.name(), TAG_MIN_LENGTH, TAG_MAX_LENGTH, "CHECK_TAG_NAME_LENGTH");
    }

    public void checkCommentDto(CommentRequestDTO dto){
        validateLength(dto.content(), CONTENT_MIN_LENGTH, CONTENT_MAX_LENGTH, "CHECK_CONTENT_LENGTH");
        long newsId = dto.newsId();
        if (!newsRepo.existById(newsId)){
            throw new NotFoundException(String.format(ErrorCodes.NEWS_NOT_EXIST.getMessage(), newsId));
        }
    }

    public void validateNewsExist(Long id){
        if (!newsRepo.existById(id)){
            throw new NotFoundException(String.format(ErrorCodes.NEWS_NOT_EXIST.getMessage(), id));
        }
    }

    private void validateLength(String text, int minLength, int maxLength, String error){
        if (text.length() < minLength || (text.length() > maxLength)){
            throw new ValidatorException(String.format(ErrorCodes.valueOf(error).getMessage(), minLength, maxLength, text));
        }
    }
}
