package com.santin.survey.api.v1.output;

import java.time.Instant;

public class SessionOutput {

    private Long id;
    private QuestionOutput question;
    private String description;
    private Instant startDateTime;
    private Instant finishDateTime;

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

    public Instant getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Instant startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Instant getFinishDateTime() {
        return finishDateTime;
    }

    public void setFinishDateTime(Instant finishDateTime) {
        this.finishDateTime = finishDateTime;
    }
}
