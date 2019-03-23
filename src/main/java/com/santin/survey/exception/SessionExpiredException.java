package com.santin.survey.exception;

import org.springframework.http.HttpStatus;

public class SessionExpiredException extends HttpException {

    public SessionExpiredException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.FORBIDDEN;
    }
}
