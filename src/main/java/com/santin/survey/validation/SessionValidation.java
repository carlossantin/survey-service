package com.santin.survey.validation;

import com.santin.survey.dto.SessionDto;
import com.santin.survey.exception.InputValidationException;

public class SessionValidation {

    public static void validateSessionDto(final SessionDto session) {
        if (session.getStartDateTime().isAfter(session.getFinishDateTime())) {
            throw new InputValidationException("The start datetime must be lower than the finish datetime");
        }
    }
}
