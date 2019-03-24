package com.santin.survey.builder;

import com.santin.survey.dto.SessionAnswerCompilationResultDto;

public class SessionAnswerCompilationResultDtoBuilder {

    public static class Builder {
        private final SessionAnswerCompilationResultDto sessionAnswerCompilationResultDto = new SessionAnswerCompilationResultDto();

        public Builder() {
            sessionAnswerCompilationResultDto.setValue("SIM");
            sessionAnswerCompilationResultDto.setAmount(10L);
        }

        public SessionAnswerCompilationResultDto build() {
            return sessionAnswerCompilationResultDto;
        }
    }
}
