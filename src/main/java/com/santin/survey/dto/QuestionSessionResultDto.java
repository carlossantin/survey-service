package com.santin.survey.dto;

import java.time.LocalDateTime;
import java.util.Set;

public class QuestionSessionResultDto {

    private Long sessionId;
    private String sessionDescription;
    private LocalDateTime finishDateTime;
    private Set<SessionAnswerCompilationResultDto> answers;

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

    public Set<SessionAnswerCompilationResultDto> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<SessionAnswerCompilationResultDto> answers) {
        this.answers = answers;
    }
}
