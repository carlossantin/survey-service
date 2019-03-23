package com.santin.survey.api.v1.input;

import javax.validation.constraints.NotBlank;

public class QuestionInput {

    @NotBlank(message = "The questionDescription field is mandatory")
    private String questionDescription;

    public String getQuestionDescription() {
        return questionDescription;
    }

    public void setQuestionDescription(String questionDescription) {
        this.questionDescription = questionDescription;
    }
}
