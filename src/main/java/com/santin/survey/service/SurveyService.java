package com.santin.survey.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.santin.survey.dto.AnswerDto;
import com.santin.survey.dto.QuestionAnswerCompilationResultDto;
import com.santin.survey.dto.QuestionDto;
import com.santin.survey.dto.QuestionResultDto;
import com.santin.survey.dto.QuestionSessionResultDto;
import com.santin.survey.dto.SessionAnswerCompilationResultDto;
import com.santin.survey.dto.SessionDto;
import com.santin.survey.dto.SessionResultDto;
import com.santin.survey.entity.Answer;
import com.santin.survey.entity.AnswerId;
import com.santin.survey.entity.Question;
import com.santin.survey.entity.Session;
import com.santin.survey.exception.DuplicatedVoteException;
import com.santin.survey.exception.QuestionNotFoundException;
import com.santin.survey.exception.SessionNotFoundException;
import com.santin.survey.repository.AnswerRepository;
import com.santin.survey.repository.QuestionCompilationRepository;
import com.santin.survey.repository.QuestionRepository;
import com.santin.survey.repository.SessionCompilationRepository;
import com.santin.survey.repository.SessionRepository;
import com.santin.survey.validation.AnswerValidation;
import com.santin.survey.validation.SessionValidation;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SurveyService {

    private final QuestionRepository questionRepository;
    private final SessionRepository sessionRepository;
    private final AnswerRepository answerRepository;
    private final SessionCompilationRepository sessionCompilationRepository;
    private final QuestionCompilationRepository questionCompilationRepository;
    private final ObjectMapper objectMapper;

    private static final long DEFAULT_SESSION_DURATION_SECONDS = 60L;

    public SurveyService(final QuestionRepository questionRepository,
                         final SessionRepository sessionRepository,
                         final AnswerRepository answerRepository,
                         final SessionCompilationRepository sessionCompilationRepository,
                         final QuestionCompilationRepository questionCompilationRepository,
                         final ObjectMapper objectMapper) {
        this.questionRepository = questionRepository;
        this.sessionRepository = sessionRepository;
        this.answerRepository = answerRepository;
        this.sessionCompilationRepository = sessionCompilationRepository;
        this.questionCompilationRepository = questionCompilationRepository;
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

    public SessionResultDto getSessionResult(Long sessionId) {
        Optional<Session> session = sessionRepository.findById(sessionId);
        if (!session.isPresent()) {
            throw new SessionNotFoundException(String.format("The session having ID = %s does not exist", sessionId));
        }

        Set<SessionAnswerCompilationResultDto> resultsForSession =
                new HashSet<>(sessionCompilationRepository.getResultsForSessionBySessionId(sessionId));
        SessionResultDto sessionResultDto = new SessionResultDto();
        sessionResultDto.setSession(objectMapper.convertValue(session.get(), SessionDto.class));
        sessionResultDto.setAnswers(resultsForSession);
        return sessionResultDto;
    }

    public QuestionResultDto getQuestionResult(Long questionId) {
        Optional<Question> question = questionRepository.findById(questionId);
        if (!question.isPresent()) {
            throw new QuestionNotFoundException(String.format("The question having ID = %s does not exist", questionId));
        }

        Map<Long, List<QuestionAnswerCompilationResultDto>> sessions = questionCompilationRepository.getResultsForQuestionByQuestionId(questionId)
                .stream().collect(Collectors.groupingBy(QuestionAnswerCompilationResultDto::getSessionId));

        QuestionResultDto questionResultDto = new QuestionResultDto();
        questionResultDto.setId(questionId);
        questionResultDto.setDescription(question.get().getDescription());
        questionResultDto.setSessions(sessions.entrySet().stream().map(this::toQuestionSessionResultDto).collect(Collectors.toSet()));

        return questionResultDto;
    }

    private QuestionSessionResultDto toQuestionSessionResultDto(Map.Entry<Long, List<QuestionAnswerCompilationResultDto>> questionAnswerMapEntry) {
        QuestionSessionResultDto questionSessionResultDto = new QuestionSessionResultDto();
        questionSessionResultDto.setSessionId(questionAnswerMapEntry.getKey());
        questionSessionResultDto.setSessionDescription(questionAnswerMapEntry.getValue().get(0).getSessionDescription());
        questionSessionResultDto.setFinishDateTime(questionAnswerMapEntry.getValue().get(0).getFinishDateTime());
        questionSessionResultDto.setAnswers(questionAnswerMapEntry.getValue().stream().map(this::toSessionAnswerCompilationResultDto).collect(Collectors.toSet()));
        return questionSessionResultDto;
    }

    private SessionAnswerCompilationResultDto toSessionAnswerCompilationResultDto(final QuestionAnswerCompilationResultDto questionAnswerCompilationResultDto) {
        SessionAnswerCompilationResultDto sessionAnswerCompilationResultDto = new SessionAnswerCompilationResultDto();
        sessionAnswerCompilationResultDto.setValue(questionAnswerCompilationResultDto.getValue());
        sessionAnswerCompilationResultDto.setAmount(questionAnswerCompilationResultDto.getAmount());
        return sessionAnswerCompilationResultDto;
    }

}
