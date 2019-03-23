package com.santin.survey.builder;

import com.santin.survey.dto.QuestionDto;

public class QuestionDtoBuilder {

    public static class Builder {
        private final QuestionDto questionDto = new QuestionDto();

        public Builder() {
            questionDto.setDescription("Description");
            questionDto.setId(1L);
        }

        public QuestionDto build() {
            return questionDto;
        }
    }
}
