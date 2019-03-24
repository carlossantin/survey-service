package com.santin.survey.api.v1.output;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class SessionResultOutput {

    private SessionOutput session;
    private Set<AnswerAmountOutput> answers = new HashSet<>();

    public SessionOutput getSession() {
        return session;
    }

    public void setSession(SessionOutput session) {
        this.session = session;
    }

    public Set<AnswerAmountOutput> getAnswers() {
        return answers;
    }

    public boolean isFinished() {
        return session.getFinishDateTime().isBefore(LocalDateTime.now());
    }

    public Long getTotalVotes() {
        return answers.stream()
                .mapToLong(AnswerAmountOutput::getAmount)
                .sum();
    }

}
