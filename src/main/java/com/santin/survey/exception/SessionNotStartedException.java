package com.santin.survey.exception;

import org.springframework.http.HttpStatus;

public class SessionNotStartedException extends HttpException {

    public SessionNotStartedException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.FORBIDDEN;
    }
}
