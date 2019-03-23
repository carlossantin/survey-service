package com.santin.survey.exception;

import org.springframework.http.HttpStatus;

public class DuplicatedVoteException extends HttpException {

    public DuplicatedVoteException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.CONFLICT;
    }
}
