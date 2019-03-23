package com.santin.survey.exception;

import org.springframework.http.HttpStatus;

public class QuestionNotFoundException extends HttpException {

    public QuestionNotFoundException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
