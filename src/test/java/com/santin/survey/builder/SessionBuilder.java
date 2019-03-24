package com.santin.survey.builder;

import com.santin.survey.entity.Session;

import java.time.LocalDateTime;

public class SessionBuilder {

    public static class Builder {
        private final Session session = new Session();

        public Builder() {
            session.setId(1L);
            session.setQuestion(new QuestionBuilder.Builder().build());
            session.setStartDateTime(LocalDateTime.now());
            session.setFinishDateTime(session.getStartDateTime().plusMinutes(1));
            session.setDescription("Session description");
        }

        public Builder withId(Long id) {
            session.setId(id);
            return this;
        }

        public Session build() {
            return session;
        }
    }
}
