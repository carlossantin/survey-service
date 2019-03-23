package com.santin.survey.builder;

import com.santin.survey.dto.AnswerDto;

import java.time.LocalDateTime;

public class AnswerDtoBuilder {

    public static class Builder {
        private final AnswerDto answerDto = new AnswerDto();

        public Builder() {
            answerDto.setSession(new SessionDtoBuilder.Builder().build());
            answerDto.setUserId(2L);
            answerDto.setValue("SIM");
        }

        public Builder withValue(final String value) {
            answerDto.setValue(value);
            return this;
        }

        public Builder withSessionFinishDateTime(final LocalDateTime finishDateTime) {
            answerDto.getSession().setFinishDateTime(finishDateTime);
            return this;
        }

        public Builder withSessionStartDateTime(final LocalDateTime startDateTime) {
            answerDto.getSession().setStartDateTime(startDateTime);
            return this;
        }

        public AnswerDto build() {
            return answerDto;
        }
    }
}
