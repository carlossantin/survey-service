package com.santin.survey.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class AnswerId implements Serializable {

    @Column
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "session_id")
    private Session session;

    public AnswerId() {
    }

    public AnswerId(Long userId, Session session) {
        this.userId = userId;
        this.session = session;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnswerId answerId = (AnswerId) o;
        return Objects.equals(userId, answerId.userId) &&
                Objects.equals(session, answerId.session);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, session);
    }

    @Override
    public String toString() {
        return "{\"AnswerId\":{"
                + "\"userId\":\"" + userId + "\""
                + ", \"session\":" + session
                + "}}";
    }
}
