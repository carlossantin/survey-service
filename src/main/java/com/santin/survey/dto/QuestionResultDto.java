package com.santin.survey.dto;

import java.util.Set;

public class QuestionResultDto {

    private Long id;
    private String description;
    private Set<QuestionSessionResultDto> sessions;

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

    public Set<QuestionSessionResultDto> getSessions() {
        return sessions;
    }

    public void setSessions(Set<QuestionSessionResultDto> sessions) {
        this.sessions = sessions;
    }

}
