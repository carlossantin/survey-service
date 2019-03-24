package com.santin.survey.builder;

import com.santin.survey.dto.QuestionAnswerCompilationResultDto;

import java.time.LocalDateTime;

public class QuestionAnswerCompilationResultDtoBuilder {

    public static class Builder {
        private final QuestionAnswerCompilationResultDto questionAnswerCompilationResultDto = new QuestionAnswerCompilationResultDto();

        public Builder() {
            questionAnswerCompilationResultDto.setValue("SIM");
            questionAnswerCompilationResultDto.setAmount(10L);
            questionAnswerCompilationResultDto.setFinishDateTime(LocalDateTime.now().plusMinutes(1));
            questionAnswerCompilationResultDto.setQuestionDescription("Question description");
            questionAnswerCompilationResultDto.setQuestionId(1L);
            questionAnswerCompilationResultDto.setSessionDescription("Session description");
            questionAnswerCompilationResultDto.setSessionId(2L);
        }

        public QuestionAnswerCompilationResultDto build() {
            return questionAnswerCompilationResultDto;
        }
    }
}
