package com.santin.survey.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.santin.survey.builder.SessionDtoBuilder;
import com.santin.survey.dto.QuestionDto;
import com.santin.survey.dto.SessionDto;
import com.santin.survey.entity.Question;
import com.santin.survey.entity.Session;
import com.santin.survey.exception.InputValidationException;
import com.santin.survey.exception.QuestionNotFoundException;
import com.santin.survey.repository.QuestionRepository;
import com.santin.survey.repository.SessionRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.time.Instant;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SurveyServiceTest {

    @InjectMocks
    private SurveyService surveyService;
    @Mock
    private QuestionRepository questionRepository;
    @Mock
    private SessionRepository sessionRepository;
    @Spy
    private ObjectMapper objectMapper;

    @Before
    public void configureMapper() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

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

    @Test(expected = QuestionNotFoundException.class)
    public void testGettingNonExistingQuestionId() {
        mockGettingNonExistingQuestionById();
        surveyService.getQuestion(1L);
    }

    private void mockGettingNonExistingQuestionById() {
        when(questionRepository.findById(any())).thenReturn(Optional.empty());
    }

    @Test(expected = InputValidationException.class)
    public void testCreatingSessionStartDateTimeHigherFinalDateTime() {
        SessionDto sessionDto = new SessionDtoBuilder.Builder()
                .withFinishDateTime(Instant.now().minusSeconds(400))
                .build();

        surveyService.createSession(sessionDto);
    }

    @Test
    public void testCreatingSessionWithoutFinishDateTime() {
        SessionDto sessionDto = new SessionDtoBuilder.Builder()
                .withFinishDateTime(null)
                .build();

        final Long expectedId = 10L;
        mockSaveSession(expectedId);

        SessionDto session = surveyService.createSession(sessionDto);

        Assert.assertEquals(expectedId, session.getId());
        Assert.assertEquals(session.getStartDateTime().plusSeconds(60), session.getFinishDateTime());
    }

    private void mockSaveSession(final Long sessionId) {
        doAnswer(new Answer<Session>() {
            @Override
            public Session answer(InvocationOnMock invocation) throws Throwable {
                final Session receivedSession = invocation.getArgument(0);
                receivedSession.setId(sessionId);
                return receivedSession;
            }
        }).when(sessionRepository).save(any());
    }
}
