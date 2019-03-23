package com.santin.survey.api.v1.output;

public class ErrorOutput {

    private final String message;

    public ErrorOutput(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
