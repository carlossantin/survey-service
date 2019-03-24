package com.santin.survey.api.v1.output;

import java.time.LocalDateTime;
import java.util.Set;

public class QuestionSessionResultOutput {

    private Long sessionId;
    private String sessionDescription;
    private LocalDateTime finishDateTime;
    private Set<AnswerAmountOutput> answers;

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

    public Set<AnswerAmountOutput> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<AnswerAmountOutput> answers) {
        this.answers = answers;
    }

    public boolean isFinished() {
        return getFinishDateTime().isBefore(LocalDateTime.now());
    }

    public Long getTotalVotes() {
        return answers.stream()
                .mapToLong(AnswerAmountOutput::getAmount)
                .sum();
    }

    @Override
    public String toString() {
        return "{\"QuestionSessionResultOutput\":{"
                + "\"sessionId\":\"" + sessionId + "\""
                + ", \"sessionDescription\":\"" + sessionDescription + "\""
                + ", \"finishDateTime\":" + finishDateTime
                + ", \"answers\":" + answers
                + "}}";
    }
}
