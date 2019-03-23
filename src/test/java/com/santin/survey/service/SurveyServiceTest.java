package com.santin.survey.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.santin.survey.builder.AnswerDtoBuilder;
import com.santin.survey.builder.SessionDtoBuilder;
import com.santin.survey.dto.AnswerDto;
import com.santin.survey.dto.QuestionDto;
import com.santin.survey.dto.SessionDto;
import com.santin.survey.entity.Question;
import com.santin.survey.entity.Session;
import com.santin.survey.exception.DuplicatedVoteException;
import com.santin.survey.exception.InputValidationException;
import com.santin.survey.exception.NotValidAnswerException;
import com.santin.survey.exception.QuestionNotFoundException;
import com.santin.survey.exception.SessionExpiredException;
import com.santin.survey.exception.SessionNotStartedException;
import com.santin.survey.repository.AnswerRepository;
import com.santin.survey.repository.QuestionRepository;
import com.santin.survey.repository.SessionRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SurveyServiceTest {

    @InjectMocks
    private SurveyService surveyService;
    @Mock
    private QuestionRepository questionRepository;
    @Mock
    private SessionRepository sessionRepository;
    @Mock
    private AnswerRepository answerRepository;
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
        doAnswer((Answer<Question>) invocation -> {
            final Question receivedQuestion = invocation.getArgument(0);
            receivedQuestion.setId(questionId);
            return receivedQuestion;
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
                .withFinishDateTime(LocalDateTime.now().minusSeconds(400))
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
        doAnswer((Answer<Session>) invocation -> {
            final Session receivedSession = invocation.getArgument(0);
            receivedSession.setId(sessionId);
            return receivedSession;
        }).when(sessionRepository).save(any());
    }

    @Test(expected = DuplicatedVoteException.class)
    public void testDuplicatedVote() {
        final AnswerDto answerDto = new AnswerDtoBuilder.Builder().build();
        mockDataIntegrityExceptionOnSaveVote();

        surveyService.vote(answerDto);
    }

    private void mockDataIntegrityExceptionOnSaveVote() {
        doThrow(new DataIntegrityViolationException("")).when(answerRepository).insertAnswer(any());
    }

    @Test(expected = SessionExpiredException.class)
    public void testVoteAfterSessionFinished() {
        final AnswerDto answerDto = new AnswerDtoBuilder.Builder()
                .withSessionFinishDateTime(LocalDateTime.now().minusHours(1))
                .build();

        surveyService.vote(answerDto);
    }

    @Test(expected = SessionNotStartedException.class)
    public void testVoteBeforeSessionStart() {
        final AnswerDto answerDto = new AnswerDtoBuilder.Builder()
                .withSessionStartDateTime(LocalDateTime.now().plusHours(1))
                .build();

        surveyService.vote(answerDto);
    }

    @Test(expected = NotValidAnswerException.class)
    public void testNotValidAnswer() {
        final AnswerDto answerDto = new AnswerDtoBuilder.Builder()
                .withValue("TALVEZ")
                .build();

        surveyService.vote(answerDto);
    }

    @Test
    public void testSuccessOnVote() {
        final AnswerDto answerDto = new AnswerDtoBuilder.Builder()
                .build();

        AnswerDto vote = surveyService.vote(answerDto);
        Assert.assertEquals(answerDto, vote);
    }
}
