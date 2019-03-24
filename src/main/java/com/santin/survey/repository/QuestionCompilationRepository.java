package com.santin.survey.repository;

import com.santin.survey.dto.QuestionAnswerCompilationResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;

@Repository
public class QuestionCompilationRepository extends NamedParameterJdbcDaoSupport {

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void init() {
        setDataSource(dataSource);
    }

    public List<QuestionAnswerCompilationResultDto> getResultsForQuestionByQuestionId(Long questionId) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("questionId", questionId);

        final String sql =
                "SELECT ques.id AS question_id, ques.description AS question_description, sess.id AS session_id, " +
                        "sess.description AS session_description, sess.finish_date_time, answ.value, " +
                        "count(answ.value) AS amount " +
                        "FROM question ques, session sess, answer answ WHERE " +
                        "answ.session_id = sess.id AND sess.question_id = ques.id " +
                        "AND ques.id = :questionId " +
                        "GROUP BY ques.id, ques.description, sess.id, sess.description, sess.finish_date_time, answ.value";
        return getNamedParameterJdbcTemplate().query(sql, params, new BeanPropertyRowMapper<>(QuestionAnswerCompilationResultDto.class));
    }
}
