package com.santin.survey.api.v1.output;

import com.santin.survey.dto.SessionDto;

public class AnswerOutput {

    private Long userId;
    private String value;
    private SessionDto session;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public SessionDto getSession() {
        return session;
    }

    public void setSession(SessionDto session) {
        this.session = session;
    }
}
