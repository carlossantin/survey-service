package com.santin.survey.api.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.santin.survey.api.v1.input.AnswerInput;
import com.santin.survey.api.v1.input.QuestionInput;
import com.santin.survey.api.v1.input.SessionInput;
import com.santin.survey.api.v1.output.AnswerOutput;
import com.santin.survey.api.v1.output.QuestionOutput;
import com.santin.survey.api.v1.output.QuestionResultOutput;
import com.santin.survey.api.v1.output.SessionOutput;
import com.santin.survey.api.v1.output.SessionResultOutput;
import com.santin.survey.dto.AnswerDto;
import com.santin.survey.dto.QuestionDto;
import com.santin.survey.dto.QuestionResultDto;
import com.santin.survey.dto.SessionDto;
import com.santin.survey.dto.SessionResultDto;
import com.santin.survey.exception.InputValidationException;
import com.santin.survey.service.SurveyService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/survey")
public class SurveyApi {

    private final SurveyService surveyService;
    private final ObjectMapper objectMapper;

    public SurveyApi(SurveyService surveyService, ObjectMapper objectMapper) {
        this.surveyService = surveyService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/questions/create")
    @ApiOperation(value = "Creates a new question to be used in a survey session")
    public ResponseEntity<QuestionOutput> createQuestion(@RequestBody @Valid QuestionInput question, BindingResult result) {
        validateErrors(result);

        QuestionDto questionDto = surveyService.createQuestion(question.getQuestionDescription());
        return ResponseEntity.ok(objectMapper.convertValue(questionDto, QuestionOutput.class));
    }

    @PostMapping("/questions/{id}/sessions/create")
    @ApiOperation(value = "Creates a new session for a question to be voted by associates. " +
            "A session must be related to a saved question. " +
            "The fields description, startDateTime and finishDateTime are optional. " +
            "If you don't specify the startDateTime, it will be assumed that it is the current time. " +
            "If you don't specify the finishDateTime, it will be assumed that it is the startDateTime plus one minute.")
    public ResponseEntity<SessionOutput> createSession(@PathVariable("id") Long questionId,
                                                       @RequestBody SessionInput sessionInput) {
        final QuestionDto question = surveyService.getQuestion(questionId);

        SessionDto sessionDto = objectMapper.convertValue(sessionInput, SessionDto.class);
        sessionDto.setQuestion(question);

        sessionDto = surveyService.createSession(sessionDto);

        return ResponseEntity.ok(objectMapper.convertValue(sessionDto, SessionOutput.class));
    }

    @GetMapping("/questions/{id}")
    @ApiOperation(value = "Gets the question data and the voting result for each session related to the question")
    public ResponseEntity<QuestionResultOutput> getQuestionResult(@PathVariable("id") Long questionId) {
        QuestionResultDto questionResult = surveyService.getQuestionResult(questionId);
        return ResponseEntity.ok(objectMapper.convertValue(questionResult, QuestionResultOutput.class));
    }

    @PostMapping("/sessions/vote")
    @ApiOperation(value = "Allows an associate to vote for a question related to an opened session. The allowed values are SIM or NAO")
    public ResponseEntity<AnswerOutput> voteQuestion(@RequestBody @Valid AnswerInput answerInput, BindingResult result) {
        validateErrors(result);
        final SessionDto session = surveyService.getSession(answerInput.getSessionId());

        AnswerDto answerDto = objectMapper.convertValue(answerInput, AnswerDto.class);
        answerDto.setSession(session);

        AnswerDto vote = surveyService.vote(answerDto);

        return ResponseEntity.ok(objectMapper.convertValue(vote, AnswerOutput.class));
    }

    @GetMapping("/sessions/{id}")
    @ApiOperation(value = "Gets the session data and the voting result")
    public ResponseEntity<SessionResultOutput> getSessionResult(@PathVariable("id") Long sessionId) {
        SessionResultDto sessionResult = surveyService.getSessionResult(sessionId);
        return ResponseEntity.ok(objectMapper.convertValue(sessionResult, SessionResultOutput.class));
    }

    private void validateErrors(BindingResult result) {
        if (result.hasErrors()) {
            throw new InputValidationException(result.getAllErrors().get(0).getDefaultMessage());
        }
    }

}
