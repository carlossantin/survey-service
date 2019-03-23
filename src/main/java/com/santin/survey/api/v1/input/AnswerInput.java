package com.santin.survey.api.v1.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AnswerInput {

    @NotNull(message = "The user id is mandatory")
    private Long userId;
    @NotNull(message = "The session id is mandatory")
    private Long sessionId;
    @NotBlank(message = "The answer value is mandatory")
    private String value;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
