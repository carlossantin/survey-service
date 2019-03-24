package com.santin.survey.api.v1.output;

import java.util.Set;

public class QuestionResultOutput {

    private Long id;
    private String description;
    private Set<QuestionSessionResultOutput> sessions;

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

    public Set<QuestionSessionResultOutput> getSessions() {
        return sessions;
    }

    public void setSessions(Set<QuestionSessionResultOutput> sessions) {
        this.sessions = sessions;
    }

    public Long getTotalVotes() {
        return sessions.stream()
                .mapToLong(QuestionSessionResultOutput::getTotalVotes)
                .sum();
    }

    @Override
    public String toString() {
        return "{\"QuestionResultOutput\":{"
                + "\"id\":\"" + id + "\""
                + ", \"description\":\"" + description + "\""
                + ", \"sessions\":" + sessions
                + "}}";
    }
}
