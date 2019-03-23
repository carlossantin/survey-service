package com.santin.survey.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.santin.survey.dto.QuestionDto;
import com.santin.survey.dto.SessionDto;
import com.santin.survey.entity.Question;
import com.santin.survey.entity.Session;
import com.santin.survey.exception.QuestionNotFoundException;
import com.santin.survey.repository.QuestionRepository;
import com.santin.survey.repository.SessionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SurveyService {

    private final QuestionRepository questionRepository;
    private final SessionRepository sessionRepository;
    private final ObjectMapper objectMapper;

    public SurveyService(final QuestionRepository questionRepository,
                         final SessionRepository sessionRepository,
                         final ObjectMapper objectMapper) {
        this.questionRepository = questionRepository;
        this.sessionRepository = sessionRepository;
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
        Session session = objectMapper.convertValue(sessionDto, Session.class);
        session = sessionRepository.save(session);
        return objectMapper.convertValue(session, SessionDto.class);
    }
}
