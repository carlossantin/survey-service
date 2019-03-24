package com.santin.survey.repository;

import com.santin.survey.dto.SessionAnswerCompilationResultDto;
import com.santin.survey.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    @Query("SELECT new com.santin.survey.dto.SessionAnswerCompilationResultDto(a.value, count(a))" +
            "FROM Answer a " +
            "WHERE a.id.session.id = :sessionId " +
            "GROUP BY a.value")
    List<SessionAnswerCompilationResultDto> getResultsForSessionBySessionId(@Param("sessionId") Long sessionId);
}
