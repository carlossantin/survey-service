package com.santin.survey.dto;

public class AnswerDto {

    private Long userId;
    private String value;
    private SessionDto session;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public SessionDto getSession() {
        return session;
    }

    public void setSession(SessionDto session) {
        this.session = session;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "{\"AnswerDto\":{"
                + "\"userId\":\"" + userId + "\""
                + ", \"value\":\"" + value + "\""
                + ", \"session\":" + session
                + "}}";
    }
}
