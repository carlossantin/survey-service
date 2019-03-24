package com.santin.survey.builder;

import com.santin.survey.entity.Question;

public class QuestionBuilder {

    public static class Builder {
        private final Question question = new Question();

        public Builder() {
            question.setId(1L);
            question.setDescription("Description");
        }

        public Builder withId(Long id) {
            question.setId(id);
            return this;
        }

        public Question build() {
            return question;
        }
    }
}
