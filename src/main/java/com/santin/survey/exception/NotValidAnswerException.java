package com.santin.survey.exception;

import org.springframework.http.HttpStatus;

public class NotValidAnswerException extends HttpException {

    public NotValidAnswerException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
