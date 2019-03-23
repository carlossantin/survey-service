package com.santin.survey.exception;

import org.springframework.http.HttpStatus;

public class InputValidationException extends HttpException {

    public InputValidationException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
