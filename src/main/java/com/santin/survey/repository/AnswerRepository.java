package com.santin.survey.repository;

import com.santin.survey.entity.Answer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class AnswerRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void insertAnswer(Answer answer) {
        this.entityManager.persist(answer);
    }
}
