package com.santin.survey.dto;

import java.time.LocalDateTime;

public class QuestionAnswerCompilationResultDto {

    private Long questionId;
    private String questionDescription;
    private Long sessionId;
    private String sessionDescription;
    private LocalDateTime finishDateTime;
    private String value;
    private Long amount;

    public QuestionAnswerCompilationResultDto() {
    }

    public QuestionAnswerCompilationResultDto(Long questionId, String questionDescription, Long sessionId, String sessionDescription, LocalDateTime finishDateTime, String value, Long amount) {
        this.questionId = questionId;
        this.questionDescription = questionDescription;
        this.sessionId = sessionId;
        this.sessionDescription = sessionDescription;
        this.finishDateTime = finishDateTime;
        this.value = value;
        this.amount = amount;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getQuestionDescription() {
        return questionDescription;
    }

    public void setQuestionDescription(String questionDescription) {
        this.questionDescription = questionDescription;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionDescription() {
        return sessionDescription;
    }

    public void setSessionDescription(String sessionDescription) {
        this.sessionDescription = sessionDescription;
    }

    public LocalDateTime getFinishDateTime() {
        return finishDateTime;
    }

    public void setFinishDateTime(LocalDateTime finishDateTime) {
        this.finishDateTime = finishDateTime;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
