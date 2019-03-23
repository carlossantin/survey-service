package com.santin.survey.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.santin.survey.dto.QuestionDto;
import com.santin.survey.entity.Question;
import com.santin.survey.repository.QuestionRepository;
import org.springframework.stereotype.Service;

@Service
public class SurveyService {

    private final QuestionRepository questionRepository;
    private final ObjectMapper objectMapper;

    public SurveyService(QuestionRepository questionRepository, ObjectMapper objectMapper) {
        this.questionRepository = questionRepository;
        this.objectMapper = objectMapper;
    }

    public QuestionDto createQuestion(String questionDescription) {
        Question question = new Question(questionDescription);
        question = questionRepository.save(question);
        return objectMapper.convertValue(question, QuestionDto.class);
    }
}
