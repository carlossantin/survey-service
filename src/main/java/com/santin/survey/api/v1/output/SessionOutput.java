package com.santin.survey.api.v1.output;

import java.time.LocalDateTime;

public class SessionOutput {

    private Long id;
    private QuestionOutput question;
    private String description;
    private LocalDateTime startDateTime;
    private LocalDateTime finishDateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuestionOutput getQuestion() {
        return question;
    }

    public void setQuestion(QuestionOutput question) {
        this.question = question;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getFinishDateTime() {
        return finishDateTime;
    }

    public void setFinishDateTime(LocalDateTime finishDateTime) {
        this.finishDateTime = finishDateTime;
    }
}
