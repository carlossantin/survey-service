package com.santin.survey.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "answer")
public class Answer {

    @EmbeddedId
    private AnswerId id;

    private String value;

    public AnswerId getId() {
        return id;
    }

    public void setId(AnswerId id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "{\"Answer\":{"
                + "\"id\":" + id
                + ", \"value\":\"" + value + "\""
                + "}}";
    }
}
