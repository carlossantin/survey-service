package com.santin.survey.dto;

import java.util.HashSet;
import java.util.Set;

public class SessionResultDto {

    private SessionDto session;
    private Set<SessionAnswerCompilationResultDto> answers = new HashSet<>();

    public SessionDto getSession() {
        return session;
    }

    public void setSession(SessionDto session) {
        this.session = session;
    }

    public Set<SessionAnswerCompilationResultDto> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<SessionAnswerCompilationResultDto> answers) {
        this.answers = answers;
    }
}
