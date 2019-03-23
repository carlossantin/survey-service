package com.santin.survey.exception;

import org.springframework.http.HttpStatus;

public abstract class HttpException extends RuntimeException {

    public HttpException(String message) {
        super(message);
    }

    public abstract HttpStatus getStatus();
}
