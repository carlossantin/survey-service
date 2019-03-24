package com.santin.survey.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "session")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String description;

    @Column
    private LocalDateTime startDateTime;

    @Column
    private LocalDateTime finishDateTime;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(id, session.id) &&
                Objects.equals(description, session.description) &&
                Objects.equals(startDateTime, session.startDateTime) &&
                Objects.equals(finishDateTime, session.finishDateTime) &&
                Objects.equals(question, session.question);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, startDateTime, finishDateTime, question);
    }

    @Override
    public String toString() {
        return "{\"Session\":{"
                + "\"id\":\"" + id + "\""
                + ", \"description\":\"" + description + "\""
                + ", \"startDateTime\":" + startDateTime
                + ", \"finishDateTime\":" + finishDateTime
                + ", \"question\":" + question
                + "}}";
    }
}
