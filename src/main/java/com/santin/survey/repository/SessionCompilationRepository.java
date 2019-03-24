package com.santin.survey.repository;

import com.santin.survey.dto.SessionAnswerCompilationResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;

@Repository
public class SessionCompilationRepository extends NamedParameterJdbcDaoSupport {

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void init() {
        setDataSource(dataSource);
    }

    public List<SessionAnswerCompilationResultDto> getResultsForSessionBySessionId(Long sessionId) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("sessionId", sessionId);

        final String sql = "SELECT value, count(1) as amount " +
                "FROM answer " +
                "WHERE session_id = :sessionId " +
                "GROUP BY value";

        return getNamedParameterJdbcTemplate().query(sql, params, new BeanPropertyRowMapper<>(SessionAnswerCompilationResultDto.class));
    }

}
