package com.santin.survey.repository;

import com.santin.survey.dto.QuestionAnswerCompilationResultDto;
import com.santin.survey.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("SELECT new com.santin.survey.dto.QuestionAnswerCompilationResultDto(ques.id, ques.description, sess.id, " +
                        "sess.description, sess.finishDateTime, answ.value, count(answ.value)) " +
            "FROM Question ques, Session sess, Answer answ " +
            "WHERE answ.id.session.id = sess.id AND sess.question.id = ques.id " +
                "AND ques.id = :questionId " +
            "GROUP BY ques.id, ques.description, sess.id, sess.description, sess.finishDateTime, answ.value")
    List<QuestionAnswerCompilationResultDto> getResultsForQuestionByQuestionId(@Param("questionId") Long questionId);
}
