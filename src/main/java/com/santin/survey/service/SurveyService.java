package com.santin.survey.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.santin.survey.dto.AnswerDto;
import com.santin.survey.dto.QuestionDto;
import com.santin.survey.dto.SessionDto;
import com.santin.survey.entity.Answer;
import com.santin.survey.entity.AnswerId;
import com.santin.survey.entity.Question;
import com.santin.survey.entity.Session;
import com.santin.survey.exception.DuplicatedVoteException;
import com.santin.survey.exception.QuestionNotFoundException;
import com.santin.survey.exception.SessionNotFoundException;
import com.santin.survey.repository.AnswerRepository;
import com.santin.survey.repository.QuestionRepository;
import com.santin.survey.repository.SessionRepository;
import com.santin.survey.validation.AnswerValidation;
import com.santin.survey.validation.SessionValidation;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SurveyService {

    private final QuestionRepository questionRepository;
    private final SessionRepository sessionRepository;
    private final AnswerRepository answerRepository;
    private final ObjectMapper objectMapper;

    private static final long DEFAULT_SESSION_DURATION_SECONDS = 60L;

    public SurveyService(final QuestionRepository questionRepository,
                         final SessionRepository sessionRepository,
                         final AnswerRepository answerRepository,
                         final ObjectMapper objectMapper) {
        this.questionRepository = questionRepository;
        this.sessionRepository = sessionRepository;
        this.answerRepository = answerRepository;
        this.objectMapper = objectMapper;
    }

    public QuestionDto createQuestion(final String questionDescription) {
        Question question = new Question(questionDescription);
        question = questionRepository.save(question);
        return objectMapper.convertValue(question, QuestionDto.class);
    }

    public QuestionDto getQuestion(Long id) {
        Optional<Question> question = questionRepository.findById(id);
        if (question.isPresent()) {
            return objectMapper.convertValue(question.get(), QuestionDto.class);
        }
        throw new QuestionNotFoundException(String.format("The question having ID = %s does not exist", id));
    }

    public SessionDto createSession(final SessionDto sessionDto) {
        fixSessionStartAndFinishTimes(sessionDto);

        SessionValidation.validateSessionCreation(sessionDto);

        Session session = objectMapper.convertValue(sessionDto, Session.class);
        session = sessionRepository.save(session);
        return objectMapper.convertValue(session, SessionDto.class);
    }

    private void fixSessionStartAndFinishTimes(SessionDto sessionDto) {
        sessionDto.setStartDateTime(Optional.ofNullable(sessionDto.getStartDateTime())
                .orElse(LocalDateTime.now()));
        sessionDto.setFinishDateTime(Optional.ofNullable(sessionDto.getFinishDateTime())
                .orElse(sessionDto.getStartDateTime().plusSeconds(DEFAULT_SESSION_DURATION_SECONDS)));
    }

    public SessionDto getSession(Long id) {
        Optional<Session> session = sessionRepository.findById(id);
        if (session.isPresent()) {
            return objectMapper.convertValue(session.get(), SessionDto.class);
        }
        throw new SessionNotFoundException(String.format("The session having ID = %s does not exist", id));
    }

    public AnswerDto vote(AnswerDto answerDto) {
        SessionValidation.validateSessionNotStartedYet(answerDto.getSession());
        SessionValidation.validateSessionAlreadyFinished(answerDto.getSession());
        AnswerValidation.validateAnswer(answerDto.getValue());

        Answer answer = new Answer();
        Session session = objectMapper.convertValue(answerDto.getSession(), Session.class);
        answer.setId(new AnswerId(answerDto.getUserId(), session));
        answer.setValue(answerDto.getValue());

        try {
            answerRepository.insertAnswer(answer);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicatedVoteException(
                    String.format("The associate with ID = %s have already voted in the session with ID = %s",
                            answer.getId().getUserId(),
                            answer.getId().getSession().getId()));
        }
        return answerDto;
    }


}
