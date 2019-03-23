package com.santin.survey.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.santin.survey.dto.QuestionDto;
import com.santin.survey.entity.Question;
import com.santin.survey.repository.QuestionRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;

@RunWith(MockitoJUnitRunner.class)
public class SurveyServiceTest {

    @InjectMocks
    private SurveyService surveyService;
    @Mock
    private QuestionRepository questionRepository;
    @Spy
    private ObjectMapper objectMapper;

    @Test
    public void testCreateNewQuestion() {
        final String questionDescription = "Do you like your government?";
        final Long expectedId = 15L;
        mockSaveQuestion(expectedId);

        QuestionDto question = surveyService.createQuestion(questionDescription);

        Assert.assertEquals(expectedId, question.getId());
        Assert.assertEquals(questionDescription, question.getDescription());
    }

    private void mockSaveQuestion(final Long questionId) {
        doAnswer(new Answer<Question>() {
            @Override
            public Question answer(InvocationOnMock invocation) throws Throwable {
                final Question receivedQuestion = invocation.getArgument(0);
                receivedQuestion.setId(questionId);
                return receivedQuestion;
            }
        }).when(questionRepository).save(any());
    }
}
