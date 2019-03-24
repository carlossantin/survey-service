package com.santin.survey.validation;

import com.santin.survey.dto.SessionDto;
import com.santin.survey.exception.InputValidationException;
import com.santin.survey.exception.SessionExpiredException;
import com.santin.survey.exception.SessionNotStartedException;

import java.time.LocalDateTime;

public class SessionValidation {

    public static void validateSessionCreation(final SessionDto session) {
        if (session.getStartDateTime().isAfter(session.getFinishDateTime())) {
            throw new InputValidationException("The start datetime must be lower than the finish datetime");
        }

        validateSessionAlreadyFinished(session);
    }

    public static void validateSessionAlreadyFinished(final SessionDto session) {
        if (session.getFinishDateTime().isBefore(LocalDateTime.now()) ||
                session.getFinishDateTime().isEqual(LocalDateTime.now())) {
            throw new SessionExpiredException(String.format("The session with ID = %s has already finished", session.getId()));
        }
    }

    public static void validateSessionNotStartedYet(final SessionDto session) {
        if (session.getStartDateTime().isAfter(LocalDateTime.now())) {
            throw new SessionNotStartedException(String.format("The session with ID = %s has not started yet. It will start at %s",
                    session.getId(), session.getStartDateTime()));
        }
    }
}
