package com.santin.survey.exception;

import org.springframework.http.HttpStatus;

public class SessionNotFoundException extends HttpException {

    public SessionNotFoundException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
