package com.santin.survey.builder;

import com.santin.survey.dto.SessionDto;

import java.time.Instant;

public class SessionDtoBuilder {

    public static class Builder {
        private final SessionDto sessionDto = new SessionDto();

        public Builder() {
            sessionDto.setDescription("Description");
            sessionDto.setStartDateTime(Instant.now());
            sessionDto.setFinishDateTime(sessionDto.getStartDateTime().plusSeconds(60));
            sessionDto.setId(1L);
            sessionDto.setQuestion(new QuestionDtoBuilder.Builder().build());
        }

        public Builder withFinishDateTime(Instant datetime){
            sessionDto.setFinishDateTime(datetime);
            return this;
        }

        public SessionDto build() {
            return sessionDto;
        }
    }
}
