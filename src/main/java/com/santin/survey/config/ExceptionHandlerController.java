package com.santin.survey.config;

import com.santin.survey.api.v1.output.ErrorOutput;
import com.santin.survey.exception.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler
    public ResponseEntity handleException(HttpException throwable) {
        this.logger.error(throwable.getMessage(), throwable);
        return ResponseEntity.status(throwable.getStatus())
                .body(new ErrorOutput(throwable.getMessage()));
    }
}
