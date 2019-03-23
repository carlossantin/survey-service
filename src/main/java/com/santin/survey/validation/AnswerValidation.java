package com.santin.survey.validation;

import com.santin.survey.domain.AllowedAnswers;
import com.santin.survey.exception.NotValidAnswerException;

public class AnswerValidation {

    public static void validateAnswer(String answer) {
        if (!AllowedAnswers.isValidAnswer(answer)) {
            throw new NotValidAnswerException(String.format("The allowed answers for this question are: %s",
                    AllowedAnswers.getAllowedValues()));
        }
    }
}
