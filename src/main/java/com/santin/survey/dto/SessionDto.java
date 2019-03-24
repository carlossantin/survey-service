package com.santin.survey.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class SessionDto {

    private Long id;
    private QuestionDto question;
    private String description;
    private LocalDateTime startDateTime;
    private LocalDateTime finishDateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuestionDto getQuestion() {
        return question;
    }

    public void setQuestion(QuestionDto question) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionDto that = (SessionDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(question, that.question) &&
                Objects.equals(description, that.description) &&
                Objects.equals(startDateTime, that.startDateTime) &&
                Objects.equals(finishDateTime, that.finishDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, question, description, startDateTime, finishDateTime);
    }

    @Override
    public String toString() {
        return "{\"SessionDto\":{"
                + "\"id\":\"" + id + "\""
                + ", \"question\":" + question
                + ", \"description\":\"" + description + "\""
                + ", \"startDateTime\":" + startDateTime
                + ", \"finishDateTime\":" + finishDateTime
                + "}}";
    }
}
