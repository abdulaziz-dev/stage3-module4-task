package com.mjc.school.service.exception;

public enum ErrorCodes {
    NEWS_NOT_EXIST("0001", "News with id %d does not exist."),
    AUTHOR_NOT_EXIST("0002", "Author Id does not exist. Author Id is: %d"),
    CHECK_TITLE_LENGTH("0003", "News title can not be less than %d and more than %d symbols. News title is %s"),
    CHECK_CONTENT_LENGTH("0003", "News content can not be less than %d and more than %d symbols. News content is %s"),
    CHECK_AUTHOR_NAME_LENGTH("0003", "Author name can not be less than %d and more than %d symbols. Author name is %s"),
    CHECK_TAG_NAME_LENGTH("0003", "Author name can not be less than %d and more than %d symbols. Author name is %s"),
    CHECK_SHOULD_BE_NUMBER("0004", "%s should be number"),
    CHECK_NEGATIVE_OR_NULL_NUMBER("0005", "%s can not be null or less than 1. %s is: %s"),
    TAG_NOT_EXIST("0006", "Tag with id %d does not exist."),
    COMMENT_NOT_EXIST("0007", "Comment with id %d does not exist.");

    private final String code_id;
    private final String code_message;

    ErrorCodes(String code_id, String code_message) {
        this.code_id = code_id;
        this.code_message = code_message;
    }

    public String getMessage() {
        return "ERROR_CODE: " + code_id + " ERROR_MESSAGE: " + code_message;
    }
}